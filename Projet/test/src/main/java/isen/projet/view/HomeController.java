package isen.projet.view;

import java.io.IOException;

import isen.projet.App;
import javafx.fxml.FXML;

public class HomeController {

	@FXML
	public void handleLaunchButton() throws IOException {
		App.setRoot("/isen/projet/view/ListContact");
	}
}
