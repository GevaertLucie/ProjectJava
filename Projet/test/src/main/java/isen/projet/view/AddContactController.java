package isen.projet.view;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import isen.projet.App;
import isen.projet.db.entities.Person;
import isen.projet.db.entities.PersonService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddContactController {

	@FXML
	public Person currentPerson ;

	@FXML
	public TextField inputFirstName; 

	@FXML
	public TextField inputLastName; 

	@FXML
	public TextField inputNickName; 

	@FXML
	public TextField inputPhoneNumber; 

	@FXML
	public TextField inputAddress; 

	@FXML
	public TextField inputEmailAddress; 

	@FXML
	public TextField inputBirthDate; 
	
	private Person contact;
	private Stage editStage;

	/** Adds the contact to the database and the visible TableView and returns to HomeScreen */
	@FXML
	public void handleAddButton() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(format.parse(this.inputBirthDate.getText()).getTime());
		this.contact = new Person(1, this.inputLastName.getText(), this.inputFirstName.getText(), this.inputNickName.getText(), 
				this.inputPhoneNumber.getText(), this.inputAddress.getText(), this.inputEmailAddress.getText(), date);

		PersonService.addPerson(App.personDao.addPerson(this.contact));
		editStage.close();
	}

	/** Returns to Home Screen without adding a new contact	 */
	@FXML
	public void handleCancelButton() throws IOException {
		App.showView("HomeScreen");
	}
	
    /** Sets the stage of this dialog */
    public void setEditStage(Stage editStage) {
        this.editStage = editStage;
    }
}
