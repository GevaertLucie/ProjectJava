package isen.projet.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import org.kordamp.bootstrapfx.BootstrapFX;

import isen.projet.App;
import isen.projet.db.daos.DataSourceFactory;
import isen.projet.db.entities.Person;
import isen.projet.db.entities.PersonService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HomeScreenController {

	@FXML
	public TableView<Person> personsTable ;

	@FXML
	public TableColumn<Person, String> lastnameColumn ;

	@FXML
	public TableColumn<Person, String> firstnameColumn ;

	@FXML
	public GridPane gridPane ;

	public Person currentPerson ;

	// Labels containing the info of selected contact
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label firstNameLabel;
	@FXML
	private Label nicknameLabel;
	@FXML
	private Label birthDateLabel;
	@FXML
	private Label addressLabel;
	@FXML
	private Label phoneNumberLabel;
	@FXML
	private Label emailAddressLabel;

	public void initDB() throws Exception {
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS person (\r\n"
				+ "    idperson INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \r\n"
				+ "    lastname VARCHAR(45) NOT NULL,  \r\n"
				+ "    firstname VARCHAR(45) NOT NULL,\r\n"
				+ "    nickname VARCHAR(45) NOT NULL,\r\n"
				+ "    phone_number VARCHAR(15) NULL,\r\n"
				+ "    address VARCHAR(200) NULL,\r\n"
				+ "    email_address VARCHAR(150) NULL,\r\n"
				+ "    birth_date DATE NULL);");

		stmt.executeUpdate("DELETE FROM person");
		stmt.executeUpdate("INSERT INTO person(idperson, lastname, firstname, nickname, phone_number, address, email_address, birth_date)" 
				+ "VALUES (1,'Last Name 1', 'First name 1', 'Nick Name 1', '0601010101', 'address 1', 'email 1', '2001-01-10 11:00:00.000')");
		stmt.executeUpdate("INSERT INTO person(idperson, lastname, firstname, nickname, phone_number, address, email_address, birth_date)" 
				+ "VALUES (2,'Last Name 2', 'First name 2', 'Nick Name 2', '0602020202', 'address 2', 'email 2', '2002-02-20 22:00:00.000')");
		stmt.executeUpdate("INSERT INTO person(idperson, lastname, firstname, nickname, phone_number, address, email_address, birth_date)" 
				+ "VALUES (3,'Last Name 3', 'First name 3', 'Nick Name 3', '0603030303', 'address 3', 'email 3', '2003-03-30 00:30:00.000')");

		stmt.close();
		connection.close();
	}

	/** Goes to the view to create a contact	*/
	@FXML
	public void handleCreateButton() throws IOException {
		//App.showView("AddContact");
		try {
			// Load FXML file and create a new stage for the popup dialog
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AddContact.fxml"));
			Parent root = loader.load();	

			// Create the edit stage
			Stage editStage = new Stage();
			editStage.setTitle("Add Contact");
			Scene scene = new Scene(root);
			scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
			editStage.setScene(scene);

			// Set the selected person into the controller
			AddContactController AddContactController = loader.getController();
			AddContactController.setEditStage(editStage);
			//AddContactController.setTextFields(currentPerson);

			// Show the edit window and wait until closed
			editStage.showAndWait();
			this.resetView();

		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** Creates a backup of all the contacts with vCard format	*/
	@FXML
	public void handleBackupButton() throws IOException {
		App.personDao.export(App.personDao.listPersons());
	}

	/**
	 * Initialization of the contact view
	 * 1. Populates the Table with all the contacts
	 * 2. Use of a listener to show the contact details when we select one
	 * */
	@FXML
	private void initialize() {    	
		try {
			this.initDB();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//App.personDao.listPersons();
		this.populateList();

		this.personsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {
			@Override
			public void changed(ObservableValue<? extends Person> observable, Person oldValue, Person newValue)  {
				showContactDetails(newValue) ;
			}
		});

		this.resetView();
	}

	/** Deletes the selected contact	*/
	@FXML
	public void handleDeleteButton() throws IOException {
		int selectedIndex = this.personsTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			App.personDao.deletePerson(personsTable.getItems().remove(selectedIndex));
			resetView();
		}
	}

	/** Enables ability to update the selected contact by going to the Edit view	*/
	@FXML
	public void handleEditButton() throws IOException {
		try {
			// Load FXML file and create a new stage for the popup dialog
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EditContact.fxml"));
			Parent root = loader.load();	

			// Create the edit stage
			Stage editStage = new Stage();
			editStage.setTitle("Edit Contact");
			Scene scene = new Scene(root);
			scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
			editStage.setScene(scene);

			// Set the selected person into the controller
			EditContactController EditContactController = loader.getController();
			EditContactController.setEditStage(editStage);
			EditContactController.setTextFields(currentPerson);

			// Show the edit window and wait until closed
			editStage.showAndWait();
			this.resetView();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the items of personsTable then refreshes the list
	 */
	@FXML
	private void populateList() {
		this.personsTable.setItems(PersonService.getPersons());
		this.lastnameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
		this.firstnameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
		this.personsTable.refresh();
	}

	/**
	 * If no contact is selected, doesn't show anything on the right.
	 * Otherwise, will display the full contact info
	 * */
	private void showContactDetails(Person person) {
		if (person == null) {
			this.gridPane.setVisible(false);
		} else {
			this.gridPane.setVisible(true);
			this.currentPerson = person ;
			this.lastNameLabel.setText(currentPerson.getLastName());
			this.firstNameLabel.setText(currentPerson.getFirstName());
			this.nicknameLabel.setText(currentPerson.getNickName());
			this.birthDateLabel.setText(currentPerson.getBirthDate().toString());
			this.addressLabel.setText(currentPerson.getAdress());
			this.phoneNumberLabel.setText(currentPerson.getPhoneNumber());
			this.emailAddressLabel.setText(currentPerson.getEmailAdress());
		}
	}

	/**
	 * refreshes the questionsTable (take a look at its available methods) and
	 * clears its selection model.
	 */
	private void refreshList() {
		this.personsTable.refresh();
		this.personsTable.getSelectionModel().clearSelection();
	}

	/**
	 * Reset our actual view with a non visible form
	 */
	private void resetView() {
		this.showContactDetails(null);
		this.refreshList();
	}
}
