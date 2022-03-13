package isen.projet.view;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import isen.projet.App;
import isen.projet.db.daos.PersonDao;
import isen.projet.db.entities.Person;
import isen.projet.db.entities.PersonService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
	private Stage addStage;

	/** Adds the contact to the database and the visible TableView and returns to HomeScreen */
	@FXML
	public void handleAddButton() throws Exception {
		if(isInputValid()) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date(format.parse(this.inputBirthDate.getText()).getTime());
			this.contact = new Person(1, this.inputLastName.getText(), this.inputFirstName.getText(), this.inputNickName.getText(), 
					this.inputPhoneNumber.getText(), this.inputAddress.getText(), this.inputEmailAddress.getText(), date);
	
			PersonService.addPerson(App.personDao.addPerson(this.contact));
			addStage.close();
		}
	}
	
    /** Sets the stage of this dialog */
    public void setAddStage(Stage addStage) {
        this.addStage = addStage;
    }

	/** Returns to Home Screen without adding a new contact	 */
	@FXML
	public void handleCancelButton() throws IOException {
		App.showView("HomeScreen");
	}
	
	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
    private boolean isInputValid() {
        String errorMessage = "";

        if (inputLastName.getText() == null || inputLastName.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }
        if (inputFirstName.getText() == null || inputFirstName.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (inputNickName.getText() == null || inputNickName.getText().length() == 0) {
            errorMessage += "No valid nickname!\n"; 
        }
        if (inputBirthDate.getText() == null || inputBirthDate.getText().length() != 10) {
            errorMessage += "No valid birthday!\n";
        }
        if (inputPhoneNumber.getText() == null || inputPhoneNumber.getText().length() == 0) {
            errorMessage += "No valid phone number!\n"; 
        } else {
            // try to parse the phone number into an int.
            try {
                Integer.parseInt(inputPhoneNumber.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid phone number! Use XX...X format\n"; 
            }
        }
        if (inputAddress.getText() == null || inputAddress.getText().length() == 0) {
            errorMessage += "No valid address!\n";
        }
        if (inputEmailAddress.getText() == null || inputEmailAddress.getText().length() == 0) {
            errorMessage += "No valid email address!\n"; 
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(addStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }
}
