package gui.java.presentation.fx.inputcontroller;

import gui.java.presentation.fx.model.ChatWindowModel;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import networking.peertopeer.Peer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Executes a gui controller for the chat window
 */
public class ChatWindowController extends BaseController implements Initializable {

    @FXML
    private ListView<HBoxCell> chatPane;

    @FXML
    private TextArea messageBox;

    @FXML
    private Button buttonSend;

    private ObservableList<HBoxCell> chatMessages = FXCollections.observableArrayList();

    private ChatWindowModel chatWindowModel;

    private Peer peer;

    /**
     * Initializes the controller after its root element has been completely processed.
     * @param url The url used to resolve relative paths
     * @param rb The resource bundle to be used
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setI18NBundle(rb);

        // disable send button if there is no message
        BooleanBinding booleanBind = messageBox.textProperty().isEmpty();
        buttonSend.disableProperty().bind(booleanBind);

        chatPane.setItems(chatMessages);//attach the observablelist to the listview

    }

    /**
     * Sets the model and the bidirectional bindings for the chat window
     * @param model The model to be set
     */
    public void setModel(ChatWindowModel model) {
        this.chatWindowModel = model;

        messageBox.textProperty().bindBidirectional(chatWindowModel.messageProperty());
    }

    public void setPeer(Peer peer){
        this.peer = peer;
        peer.setChatWindowController(this);
    }

    /**
     * Determines what happens when the send button is pressed
     * @param event The event called
     */
    @FXML
    private void sendMessageAction(ActionEvent event) {
        String msg = messageBox.getText();
        peer.communicate(msg);
        chatWindowModel.clearProperties();

        chatMessages.add(new HBoxCell(peer.getUsername(), msg, true));
    }

    public void receiveMessage(String username, String msg){
        chatMessages.add(new HBoxCell(peer.getUsername(), msg, false));
    }

    public static class HBoxCell extends HBox {
        Label usernameText = new Label();
        Label messageText = new Label();

        HBoxCell(String usernameTxt, String msgTxt, boolean fromSelf) {
            super();

            this.getChildren().addAll(usernameText, messageText);
            this.setSpacing(5);

            HBox.setHgrow(messageText, Priority.ALWAYS);

            if (fromSelf) {
                this.setAlignment(Pos.CENTER_RIGHT);

                messageText.setText(msgTxt);
            }
            else {
                usernameText.setText(usernameTxt);
                messageText.setText(msgTxt);
            }
        }
    }

}
