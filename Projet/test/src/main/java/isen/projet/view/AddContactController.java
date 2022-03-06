package isen.projet.view;

import java.sql.Date;
import java.text.SimpleDateFormat;

import isen.projet.App;
import isen.projet.db.daos.PersonDao;
import isen.projet.db.entities.Person;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddContactController {

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
	 
	@FXML
	public void initialize() {
		
	}
		
	@FXML
	public void handleButtonAdd() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(format.parse(this.inputBirthDate.getText()).getTime());
		Person p = new Person(1, this.inputLastName.getText(), this.inputFirstName.getText(), this.inputNickName.getText(), this.inputPhoneNumber.getText(), this.inputAddress.getText(), this.inputEmailAddress.getText(), date);
		App.personDao.addPerson(p);
		App.setRoot("/isen/projet/view/ListContact");
	}
}
