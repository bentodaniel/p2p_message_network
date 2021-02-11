package gui.java.presentation.fx.model;

import javafx.beans.property.*;

/**
 * Executes a gui model of the chat window
 */
public class ChatWindowModel {

    private final StringProperty message;

    /**
     * Executes the constructor for the model
     */
    public ChatWindowModel() {
       message = new SimpleStringProperty();
    }

    /** GETTERS */

    public String getMessage() {
        return message.get();
    }

    /** SETTERS */

    public void setMessage(String lessonName) {
        this.message.set(lessonName);
    }

    /** PROPERTIES */

    public StringProperty messageProperty() {
        return message;
    }

    /**
     * Clears the model's properties
     */
    public void clearProperties() {
        message.set("");
    }

}
