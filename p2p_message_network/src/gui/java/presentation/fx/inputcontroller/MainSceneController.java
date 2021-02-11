package gui.java.presentation.fx.inputcontroller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Executes a gui controller for the parent jsp
 */
public class MainSceneController implements Initializable {

    //@FXML
    //private CreateLessonController createLessonController;

    //@FXML
    //private ActivateLessonController activateLessonController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    /**
     * Sets the models for both, the create and activate lesson use cases
     * @param createLessonModel The model to be set to the create lesson use case
     * @param activateLessonModel The model to be set to the activaye lesson use case
     */
    /*public void setModels(CreateLessonModel createLessonModel, ActivateLessonModel activateLessonModel) {
        createLessonController.setModel(createLessonModel);
        activateLessonController.setModel(activateLessonModel);
    }

     */

    /**
     * Sets the remote services for both, the create and activate lesson use cases
     * @param newLessonService The remote service to be set to the create lesson use case
     * @param activateLessonService The remote service to be set to the activate lesson use case
     */
    /*public void setServices(ICreateLessonServiceRemote newLessonService, IActivateLessonServiceRemote activateLessonService){
        createLessonController.setNewLessonService(newLessonService);
        activateLessonController.setActivateLessonService(activateLessonService);
    }

     */
}
