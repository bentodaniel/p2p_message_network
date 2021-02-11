package gui.java.presentation.fx.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Executes a gui model for the create lesson use case
 */
public class CreateLessonModel {

    /*
    private final ObservableList<String> sports;
    private final StringProperty selectedSport;
    private final StringProperty lessonName;

    private final BooleanProperty weekday1;
    private final BooleanProperty weekday2;
    private final BooleanProperty weekday3;
    private final BooleanProperty weekday4;
    private final BooleanProperty weekday5;
    private final BooleanProperty weekday6;
    private final BooleanProperty weekday7;

    private final IntegerProperty startHours;
    private final IntegerProperty startMinutes;
    private final IntegerProperty duration;

     */

    /**
     * Executes the constructor for the model
     * @param newLessonService The service to be set in the model
     */
    /*public CreateLessonModel(ICreateLessonServiceRemote newLessonService) {
        this.sports = FXCollections.observableArrayList();
        selectedSport = new SimpleStringProperty();

        try {
            newLessonService.registerNewLesson().forEach(sports::add);

            if (sports.size() > 0) {
                selectedSport.setValue(sports.get(0));
            }

        } catch (ApplicationException e) {
            // no sports added
        }

        lessonName = new SimpleStringProperty();

        weekday1 = new SimpleBooleanProperty();
        weekday2 = new SimpleBooleanProperty();
        weekday3 = new SimpleBooleanProperty();
        weekday4 = new SimpleBooleanProperty();
        weekday5 = new SimpleBooleanProperty();
        weekday6 = new SimpleBooleanProperty();
        weekday7 = new SimpleBooleanProperty();

        startHours = new SimpleIntegerProperty();
        startMinutes = new SimpleIntegerProperty();
        duration = new SimpleIntegerProperty();
    }

     */

    /** GETTERS */

    /*
    public ObservableList<String> getSports() {
        return sports;
    }

    public String getSelectedSport() {
        return selectedSport.get();
    }

    public String getLessonName() {
        return lessonName.get();
    }

    public boolean isWeekday1() {
        return weekday1.get();
    }

    public boolean isWeekday2() {
        return weekday2.get();
    }

    public boolean isWeekday3() {
        return weekday3.get();
    }

    public boolean isWeekday4() {
        return weekday4.get();
    }

    public boolean isWeekday5() {
        return weekday5.get();
    }

    public boolean isWeekday6() {
        return weekday6.get();
    }

    public boolean isWeekday7() {
        return weekday7.get();
    }

    public int getStartHours() {
        return startHours.get();
    }

    public int getStartMinutes() {
        return startMinutes.get();
    }

    public int getDuration() {
        return duration.get();
    }

     */

    /** SETTERS */

    /*
    public void setSelectedSport(String selectedSport) {
        this.selectedSport.set(selectedSport);
    }

    public void setLessonName(String lessonName) {
        this.lessonName.set(lessonName);
    }

    public void setWeekday1(boolean weekday1) {
        this.weekday1.set(weekday1);
    }

    public void setWeekday2(boolean weekday2) {
        this.weekday2.set(weekday2);
    }

    public void setWeekday3(boolean weekday3) {
        this.weekday3.set(weekday3);
    }

    public void setWeekday4(boolean weekday4) {
        this.weekday4.set(weekday4);
    }

    public void setWeekday5(boolean weekday5) {
        this.weekday5.set(weekday5);
    }

    public void setWeekday6(boolean weekday6) {
        this.weekday6.set(weekday6);
    }

    public void setWeekday7(boolean weekday7) {
        this.weekday7.set(weekday7);
    }

    public void setStartHours(int startHours) {
        this.startHours.set(startHours);
    }

    public void setStartMinutes(int startMinutes) {
        this.startMinutes.set(startMinutes);
    }

    public void setDuration(int duration) {
        this.duration.set(duration);
    }

     */

    /** PROPERTIES */

    /*
    public StringProperty selectedSportProperty() {
        return selectedSport;
    }

    public StringProperty lessonNameProperty() {
        return lessonName;
    }

    public BooleanProperty weekday1Property() {
        return weekday1;
    }

    public BooleanProperty weekday2Property() {
        return weekday2;
    }

    public BooleanProperty weekday3Property() {
        return weekday3;
    }

    public BooleanProperty weekday4Property() {
        return weekday4;
    }

    public BooleanProperty weekday5Property() {
        return weekday5;
    }

    public BooleanProperty weekday6Property() {
        return weekday6;
    }

    public BooleanProperty weekday7Property() {
        return weekday7;
    }

    public IntegerProperty startHoursProperty() {
        return startHours;
    }

    public IntegerProperty startMinutesProperty() {
        return startMinutes;
    }

    public IntegerProperty durationProperty() {
        return duration;
    }

     */

    /**
     * Clears the model's properties
     */
    /*
    public void clearProperties() {
        lessonName.set("");
        weekday1.setValue(false);
        weekday2.setValue(false);
        weekday3.setValue(false);
        weekday4.setValue(false);
        weekday5.setValue(false);
        weekday6.setValue(false);
        weekday7.setValue(false);
        startHours.set(0);
        startMinutes.set(0);
        duration.set(0);
    }

     */

    /**
     * Gets an array with all the week days selected property value
     * @return A list with all the week days selected property value
     */
    /*
    public List<Boolean> getIsSelectedWeekDaysArray(){
        return new ArrayList<>(Arrays.asList(
                weekday1.get(),
                weekday2.get(),
                weekday3.get(),
                weekday4.get(),
                weekday5.get(),
                weekday6.get(),
                weekday7.get()
        ));
    }

     */
}
