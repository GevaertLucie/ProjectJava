package isen.projet.view;

import java.io.IOException;
import java.sql.Date;

import isen.projet.App;
import isen.projet.db.entities.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditContactController {
	
	@FXML
	public TextField lastnameField ;
	@FXML
	public TextField firstnameField ;
	@FXML
	public TextField nicknameField ;
	@FXML
	public TextField birthdateField ;
	@FXML
	public TextField addressField ;
	@FXML
	public TextField phonenumField ;
	@FXML
	public TextField emailField ;

	private Person currentPerson;
	
	private Stage editStage;
	private boolean saveClicked = false;
	
    /** Sets the stage of this dialog */
    public void setEditStage(Stage editStage) {
        this.editStage = editStage;
    }
    
    /** Returns true if the user clicked Save, false otherwise */
    public boolean saveClicked() {
        return saveClicked();
    }
	
    /** Sets the contact to be edited in the dialog */
	public void setTextFields(Person person) {
		this.currentPerson = person ;
		this.lastnameField.setText(currentPerson.getLastName());
		this.firstnameField.setText(currentPerson.getFirstName());
		this.nicknameField.setText(currentPerson.getNickName());
		this.birthdateField.setText(currentPerson.getBirthDate().toString());
		this.addressField.setText(currentPerson.getAdress());
		this.phonenumField.setText(currentPerson.getPhoneNumber());
		this.emailField.setText(currentPerson.getEmailAdress());
	}
	
	/** Returns to Home Screen without changing the contact, closes window	*/
	@FXML
	public void handleCancelButton() throws IOException {
		editStage.close();
	}
	
	/** Saves changes to contact and returns to HomeScreen	*/
	@FXML
	public void handleSaveButton() throws IOException {
		 if (isInputValid()) {
			 this.currentPerson.setLastName(lastnameField.getText());
			 this.currentPerson.setFirstName(firstnameField.getText());
			 this.currentPerson.setNickName(nicknameField.getText());
			 this.currentPerson.setBirthDate(Date.valueOf(birthdateField.getText()));
			 this.currentPerson.setPhoneNumber(phonenumField.getText());
			 this.currentPerson.setAdress(addressField.getText());
			 this.currentPerson.setEmailAdress(emailField.getText());

			 App.personDao.updatePerson(currentPerson);

			 saveClicked = true;
			 editStage.close();
		 }
	}
	
	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
    private boolean isInputValid() {
        String errorMessage = "";

        if (lastnameField.getText() == null || lastnameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }
        if (firstnameField.getText() == null || firstnameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (nicknameField.getText() == null || nicknameField.getText().length() == 0) {
            errorMessage += "No valid nickname!\n"; 
        }
        if (birthdateField.getText() == null || birthdateField.getText().length() != 10) {
            errorMessage += "No valid birthday!\n";
        }
        if (phonenumField.getText() == null || phonenumField.getText().length() == 0) {
            errorMessage += "No valid phone number!\n"; 
        } else {
            // try to parse the phone number into an int.
            try {
                Integer.parseInt(phonenumField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid phone number! Use XX...X format\n"; 
            }
        }
        if (addressField.getText() == null || addressField.getText().length() == 0) {
            errorMessage += "No valid address!\n";
        }
        if (emailField.getText() == null || emailField.getText().length() == 0) {
            errorMessage += "No valid email address!\n"; 
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(editStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }

}
