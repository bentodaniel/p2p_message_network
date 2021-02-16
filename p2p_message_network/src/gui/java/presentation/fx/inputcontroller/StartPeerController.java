package gui.java.presentation.fx.inputcontroller;

import gui.java.presentation.fx.model.ChatWindowModel;
import gui.java.presentation.fx.model.StartPeerModel;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import networking.exception.PeerException;
import networking.peertopeer.Peer;
import networking.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

/**
 * Executes a gui controller for the peer connection window
 */
public class StartPeerController extends BaseController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private ComboBox<String> ipComboBox;

    @FXML
    private TextField portField;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabAutomatic;

    @FXML
    private Tab tabManual;

    @FXML
    private TextField trackerIpField;

    @FXML
    private TextField trackerPortField;

    @FXML
    private TextField peersAddressesField;

    @FXML
    private CheckBox freeConnectCheckbox;

    @FXML
    private Button connectPeerBtn;

    private StartPeerModel startPeerModel;

    private boolean connectToTrackerServer;

    /**
     * Initializes the controller after its root element has been completely processed.
     * @param url The url used to resolve relative paths
     * @param rb The resource bundle to be used
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setI18NBundle(rb);

        // Limit the characters in these fields
        limitMaxLength(usernameField, 15);
        limitMaxLength(portField, 5);
        limitMaxLength(trackerIpField, 15);
        limitMaxLength(trackerPortField, 5);

        ipComboBox.getItems().add("127.0.0.1");
        try {
            ipComboBox.getItems().add(Utils.getPublicIP());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Only allow for numbers
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9]*")) {
                return change;
            }
            return null;
        };
        portField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),0, integerFilter));
        trackerPortField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),0, integerFilter));

        //set automatic as default
        tabPane.getSelectionModel().select(tabAutomatic);
        connectToTrackerServer = true;

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if(newTab.equals (tabAutomatic)) {
                connectToTrackerServer = true;
            }
            else if(newTab.equals (tabManual)) {
                connectToTrackerServer = false;
            }
            clearTabFields();
        });

        // All needed fields should be filled
        BooleanBinding booleanBind = usernameField.textProperty().isEmpty()
                .or(portField.textProperty().isEmpty())
                .or((
                        (trackerIpField.textProperty().isEmpty().or(trackerPortField.textProperty().isEmpty()))
                        .and(peersAddressesField.textProperty().isEmpty())
                        .and(freeConnectCheckbox.selectedProperty().not())
                ));

        connectPeerBtn.disableProperty().bind(booleanBind);
    }

    private void limitMaxLength(TextField textField ,int limit) {
        textField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                // Check if the new character is greater than LIMIT
                if (textField.getText().length() >= limit) {
                    textField.setText(textField.getText().substring(0, limit));
                }
            }
        });
    }

    private void clearTabFields() {
        trackerIpField.textProperty().set("");
        trackerPortField.textProperty().set("");
        peersAddressesField.textProperty().set("");
    }

    /**
     * Sets the model and the bidirectional bindings for the peer connection window
     * @param model The model to be set
     */
    public void setModel(StartPeerModel model) {
        this.startPeerModel = model;

        usernameField.textProperty().bindBidirectional(startPeerModel.usernameProperty());
        ipComboBox.valueProperty().bindBidirectional(startPeerModel.ipProperty());
        ipComboBox.getSelectionModel().select(0);
        portField.textProperty().bindBidirectional(startPeerModel.portProperty());

        trackerIpField.textProperty().bindBidirectional(startPeerModel.trackerIpProperty());
        trackerPortField.textProperty().bindBidirectional(startPeerModel.trackerPortProperty());

        peersAddressesField.textProperty().bindBidirectional(startPeerModel.peersAddressesProperty());

        freeConnectCheckbox.selectedProperty().bindBidirectional(startPeerModel.dontConnectWithAnyoneProperty());
    }

    /**
     * Determines what happens when the connect peer button is pressed
     * @param event The event called
     */
    @FXML
    void connectPeerAction(ActionEvent event) throws IOException {
        if (startPeerModel.getDontConnectWithAnyone()) {
            showInfo(i18nBundle.getString("new.peer.warning.not_connecting"), this);
        }
        else {
            startPeer(true);
        }
    }

    public void startPeer(boolean hasConnection) {
        //todo - check if the peer needs to connect

        try {
            Peer peer = new Peer(startPeerModel.getUsername(), startPeerModel.getIp(), startPeerModel.getPort());

            if (hasConnection) {
                String[] addresses;
                if (connectToTrackerServer) {
                    addresses = peer.connectToTracker(startPeerModel.getTrackerIp(), startPeerModel.getTrackerPort());
                } else {
                    addresses = startPeerModel.getPeersAddresses().split(" ");
                }
                peer.connectToPeers(addresses, true);
            }

            openChatWindow(peer);

        } catch (IOException e){
            showError(i18nBundle.getString("new.peer.error.creating") + ":\nThere was an error starting the peer");
        } catch (PeerException e) {
            showError(i18nBundle.getString("new.peer.error.creating") + ":\n" + e.getMessage());
        }
    }

    private void openChatWindow(Peer peer) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/resources/fxml/chatWindow.fxml"), i18nBundle);
        Parent root = fxmlLoader.load();

        ChatWindowController chatWindowController = fxmlLoader.getController();
        ChatWindowModel chatWindowModel = new ChatWindowModel();
        chatWindowController.setModel(chatWindowModel);
        chatWindowController.setPeer(peer);

        Stage stage = (Stage) connectPeerBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getScene().getWindow().centerOnScreen();
    }

}
