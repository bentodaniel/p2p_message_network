package gui.java.presentation.fx.inputcontroller;

import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

/**
 * Executes a gui controller for the create lesson use case
 */
public class CreateLessonController extends BaseController implements Initializable {

    @FXML
    private ComboBox<String> sportComboBox;

    @FXML
    private TextField lessonNameField;

    @FXML
    private CheckBox week_day_check_1;

    @FXML
    private CheckBox week_day_check_2;

    @FXML
    private CheckBox week_day_check_3;

    @FXML
    private CheckBox week_day_check_4;

    @FXML
    private CheckBox week_day_check_5;

    @FXML
    private CheckBox week_day_check_6;

    @FXML
    private CheckBox week_day_check_7;

    @FXML
    private Spinner<Integer> startTimeHoursSpinner;

    @FXML
    private Spinner<Integer> startTimeMinutesSpinner;

    @FXML
    private Spinner<Integer> durationSpinner;

    @FXML
    private Button createLessonBtn;

    //private CreateLessonModel createLessonModel;

    //private ICreateLessonServiceRemote newLessonService;

    /**
     * Sets the remote service for the create lesson use case
     * @param newLessonService The remote service to be set
     */
    /*public void setNewLessonService(ICreateLessonServiceRemote newLessonService) {
        this.newLessonService = newLessonService;
    }

     */

    /**
     * Sets the model and the bidirectional bindings for the create lesson use case
     * @param model The model to be set
     */
    /*public void setModel(CreateLessonModel model) {
        this.createLessonModel = model;

        sportComboBox.setItems(createLessonModel.getSports());
        sportComboBox.setValue(createLessonModel.getSelectedSport());

        lessonNameField.textProperty().bindBidirectional(createLessonModel.lessonNameProperty());

        week_day_check_1.selectedProperty().bindBidirectional(createLessonModel.weekday1Property());
        week_day_check_2.selectedProperty().bindBidirectional(createLessonModel.weekday2Property());
        week_day_check_3.selectedProperty().bindBidirectional(createLessonModel.weekday3Property());
        week_day_check_4.selectedProperty().bindBidirectional(createLessonModel.weekday4Property());
        week_day_check_5.selectedProperty().bindBidirectional(createLessonModel.weekday5Property());
        week_day_check_6.selectedProperty().bindBidirectional(createLessonModel.weekday6Property());
        week_day_check_7.selectedProperty().bindBidirectional(createLessonModel.weekday7Property());

        startTimeHoursSpinner.getEditor().textProperty().bindBidirectional(createLessonModel.startHoursProperty(), new NumberStringConverter());

        startTimeMinutesSpinner.getEditor().textProperty().bindBidirectional(createLessonModel.startMinutesProperty(), new NumberStringConverter());

        durationSpinner.getEditor().textProperty().bindBidirectional(createLessonModel.durationProperty(), new NumberStringConverter());
    }

     */

    /**
     * Initializes the controller after its root element has been completely processed.
     * @param url The url used to resolve relative paths
     * @param rb The resource bundle to be used
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setI18NBundle(rb);

        ObservableList<String> weekDaysStrings = FXCollections.observableArrayList();
        for (int i = 0; i <= 6; i++) {
            //weekDaysStrings.add(i18nBundle.getString("week.day." + i));
        }

        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9]*")) {
                return change;
            }
            return null;
        };

        durationSpinner.getEditor().setTextFormatter(new TextFormatter<>(new IntegerStringConverter(),
                0, integerFilter));

        BooleanBinding booleanBind = lessonNameField.textProperty().isEmpty()
                .or(week_day_check_1.selectedProperty().not()
                        .and(week_day_check_2.selectedProperty().not())
                        .and(week_day_check_3.selectedProperty().not())
                        .and(week_day_check_4.selectedProperty().not())
                        .and(week_day_check_5.selectedProperty().not())
                        .and(week_day_check_6.selectedProperty().not())
                        .and(week_day_check_7.selectedProperty().not())
                );

        createLessonBtn.disableProperty().bind(booleanBind);
    }

    /**
     * Determines what happens when the create lesson button is pressed
     * @param event The event called
     */
    /*@FXML
    void createLessonAction(ActionEvent event) {
        String errorMessages = validateInput();

        if (errorMessages.length() == 0) {

            List<String> weekDays = parseWeekDays(createLessonModel.getIsSelectedWeekDaysArray());
            String startTime = parseStartTime(createLessonModel.getStartHours(), createLessonModel.getStartMinutes());

            try {
                newLessonService.addNewLesson(createLessonModel.getSelectedSport(), createLessonModel.getLessonName(), weekDays, startTime, createLessonModel.getDuration());
                createLessonModel.clearProperties();

                showInfo(i18nBundle.getString("new.lesson.success"));

            } catch (ApplicationException e) {
                showError(i18nBundle.getString("new.lesson.error.creating") + ": " + e.getMessage());
            }
            
        } else{
            //showError(i18nBundle.getString("new.lesson.error.validating") + ":\n" + errorMessages);
        }
    }

     */


    /**
     * Checks if the input, i.e, the lesson name is valid
     * @return Error message if the input is invalid, else, an empty string
     */
    /*private String validateInput() {
        StringBuilder sb = new StringBuilder();
        String lessonName = createLessonModel.getLessonName();
        if (lessonName.length() != 6) {
            //sb.append(i18nBundle.getString("lesson.invalid.designation.size"));
        }
        return sb.toString();
    }

     */

    /**
     * Parses the selected week days into elegible weekdays to use in the business
     *
     * @return A list with the week days as strings
     * @param selected_week_days A list which indicates if each week day is selected or not (in order)
     */
    private List<String> parseWeekDays(List<Boolean> selected_week_days) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < selected_week_days.size(); i++){
            if (selected_week_days.get(i)){
                result.add(DayOfWeek.of(i + 1).toString());
            }
        }
        return result;
    }

    /**
     * Parses the input time into elegible time to use in the business
     * @param startHours The start hours
     * @param startMinutes The start minutes
     * @return A string with the time as 'hh:mm'
     */
    private String parseStartTime(int startHours, int startMinutes){
        String hours = String.format("%02d", startHours);
        String mins = String.format("%02d", startMinutes);

        return hours + ":" + mins;
    }

    /**
     * Determines what happens when a sport is selected
     * @param actionEvent The event called
     */
    /*@FXML
    public void sportSelected(ActionEvent actionEvent) {
        createLessonModel.setSelectedSport(sportComboBox.getValue());
    }

     */
}
