package isen.projet;

import org.kordamp.bootstrapfx.BootstrapFX ;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

import isen.projet.db.daos.PersonDao;

/**
 * JavaFX App
 * Contact App by
 * 		ANDRIANALIZAH Nirina
 * 		GEVAERT Lucie
 * 		HAMMAL Hanaa
 */
public class App extends Application {

	private static Scene scene;
	private static BorderPane mainlayout;

	public static PersonDao personDao = new PersonDao();

	@Override
	public void start(Stage stage) throws IOException {

		stage.setTitle("Contact App");
		mainlayout = loadFXML("ContactBookLayout");

		scene = new Scene(mainlayout, 840, 600);
		stage.setScene(scene);

		scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
		stage.show();

		App.showView("HomeScreen");
	}

	public static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
	}

	private static <T> T loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/isen/projet/view/" + fxml + ".fxml"));
		return fxmlLoader.load();
	}

	public static void main(String[] args) {
		launch();
	}

	public static void showView(String rootElement) {
		try {
			mainlayout.setCenter(loadFXML(rootElement));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

}