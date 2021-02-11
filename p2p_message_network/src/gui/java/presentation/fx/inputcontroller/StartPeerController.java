package gui.java.presentation.fx.inputcontroller;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

/**
 * Executes a gui controller for the activate lesson use case
 */
public class StartPeerController extends BaseController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField portField;

    @FXML
    private TextField trackerIpField;

    @FXML
    private TextField trackerPortField;

    @FXML
    private TextField peersAddressesField;

    @FXML
    private Button connectPeerBtn;

    //private ActivateLessonModel activateLessonModel;

    //private IActivateLessonServiceRemote activateLessonService;

    /**
     * Initializes the controller after its root element has been completely processed.
     * @param url The url used to resolve relative paths
     * @param rb The resource bundle to be used
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setI18NBundle(rb);

        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9]*")) {
                return change;
            }
            return null;
        };

        portField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),0, integerFilter));
        trackerPortField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),0, integerFilter));

        BooleanBinding booleanBind = usernameField.textProperty().isEmpty()
                .or(portField.textProperty().isEmpty())
                .or((
                        (trackerIpField.textProperty().isEmpty()
                                .or(trackerPortField.textProperty().isEmpty()))
                                .and(peersAddressesField.textProperty().isEmpty())
                ));

        connectPeerBtn.disableProperty().bind(booleanBind);

    }

    /**
     * Sets the model and the bidirectional bindings for the activate lesson use case
     * @param model The model to be set
     */
    /*public void setModel(ActivateLessonModel model) {
        this.activateLessonModel = model;

        spaceComboBox.setItems(activateLessonModel.getSpaces());
        spaceComboBox.setValue(activateLessonModel.getSelectedSpace());

        lessonNameField.textProperty().bindBidirectional(activateLessonModel.lessonNameProperty());

        startDatePicker.getEditor().textProperty().bindBidirectional(activateLessonModel.startDayProperty());

        endDatePicker.getEditor().textProperty().bindBidirectional(activateLessonModel.endDayProperty());

        maxParticipantsSpinner.getEditor().textProperty().bindBidirectional(activateLessonModel.maxParticipantsProperty(), new NumberStringConverter());
    }

     */

    /**
     * Sets the remote service for the activate lesson use case
     * @param activateLessonService The remote service to be set
     */
    /*public void setActivateLessonService(IActivateLessonServiceRemote activateLessonService) {
        this.activateLessonService = activateLessonService;
    }*/



    /**
     * Determines what happens when the activate lesson button is pressed
     * @param event The event called
     */
    /*@FXML
    void activateLessonAction(ActionEvent event) {
        String errorMessages = validateInput();

        if (errorMessages.length() == 0) {
            try {
                activateLessonService.activateLesson(activateLessonModel.getLessonName(), activateLessonModel.getSelectedSpace(),
                        activateLessonModel.getStartDay(), activateLessonModel.getEndDay(), activateLessonModel.getMaxParticipants());
                activateLessonModel.clearProperties();

                showInfo(i18nBundle.getString("activate.lesson.success"));

            } catch (ApplicationException e) {
                showError(i18nBundle.getString("activate.lesson.error.creating") + ": " + e.getMessage());
            }

        } else
            showError(i18nBundle.getString("activate.lesson.error.validating") + ":\n" + errorMessages);
    }
     */

    /**
     * Checks if the input, i.e, the lesson name is valid
     * @return Error message if the input is invalid, else, an empty string
     */
    /*private String validateInput() {
        StringBuilder sb = new StringBuilder();
        String lessonName = activateLessonModel.getLessonName();
        if (lessonName.length() != 6) {
            sb.append(i18nBundle.getString("lesson.invalid.designation.size"));
        }
        return sb.toString();
    }

     */

    /**
     * Determines what happens when a space is selected
     * @param actionEvent The event called
     */
    /*@FXML
    public void spaceSelected(ActionEvent actionEvent) {
        activateLessonModel.setSelectedSpace(spaceComboBox.getValue());
    }

     */
}
