package gui.java.presentation.fx.inputcontroller;

import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class BaseController {

	ResourceBundle i18nBundle;

	public void showError(String errorText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(i18nBundle.getString("error.dialog.title"));
		alert.setHeaderText(null);
		alert.setContentText(errorText);
		alert.showAndWait(); 
	}

	public void showInfo(String message, StartPeerController startPeerController) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(i18nBundle.getString("info.dialog.title"));
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait().ifPresent(response -> {
			if (response.getText().toLowerCase().equals("ok")) {
				startPeerController.startPeer(false);
			}
		});
	}

	public void setI18NBundle(ResourceBundle i18nBundle) {
		this.i18nBundle = i18nBundle;
	}


}
