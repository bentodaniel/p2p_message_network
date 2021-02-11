package gui.java.presentation.fx;

import gui.java.presentation.fx.inputcontroller.StartPeerController;
import gui.java.presentation.fx.model.StartPeerModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Startup extends Application {

	@Override 
    public void start(Stage stage) throws IOException {
    
		// This line to resolve keys against Bundle.properties
		ResourceBundle i18nBundle = ResourceBundle.getBundle(
				"gui.resources.i18n.Bundle", new Locale("en", "UK"));

		//ResourceBundle i18nBundle = ResourceBundle.getBundle(
		//		"gui.resources.i18n.Bundle", new Locale("pt", "PT"));

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/resources/fxml/startPeer.fxml"), i18nBundle);
		Parent root = fxmlLoader.load();

		StartPeerController startPeerController = fxmlLoader.getController();
		StartPeerModel startPeerModel = new StartPeerModel();
		startPeerController.setModel(startPeerModel);

		stage.setScene(new Scene(root));
		stage.setTitle(i18nBundle.getString("application.title"));
		stage.setResizable(false);
		stage.show();
    }

	public static void startGUI() {
		launch();
	}
}
