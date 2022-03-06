package isen.projet.view;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import isen.projet.App;
import isen.projet.db.daos.DataSourceFactory;
import isen.projet.db.entities.Person;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;

public class ListContactController {

	@FXML
	public TableView<List<Person>> listContact = new TableView<>();
	//public Text textListContact;
	
	private List<Person> listPerson;
	//private PersonDao personDao;
	
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
	
	@FXML
	public void initialize() {
		try {
			this.initDB();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		this.listPerson = App.personDao.listPersons();
		this.printList();
	}
	
	private void printList() {
		TableColumn<Person, String> fn = new TableColumn<Person, String>("First Name");
		TableColumn<Person, String> ln = new TableColumn<Person, String>("Last Name");
		TableColumn<Person, String> nn = new TableColumn<Person, String>("Nick Name");
		
		if (this.listContact == null) {
			//this.addRow();
			fn.setCellValueFactory(new PropertyValueFactory<>("First Name"));
			fn.setCellFactory(TextFieldTableCell.<Person> forTableColumn());
			fn.setOnEditCommit((CellEditEvent<Person, String> event) -> {
	            TablePosition<Person, String> pos = event.getTablePosition();

	            String newFullName = event.getNewValue();

	            int row = pos.getRow();
	            Person person = event.getTableView().getItems().get(row);

	            person.setFirstName(newFullName);
	            });
		}
		else {
			this.addRow();
		}
	}
	
	private void addRow() {
	      ObservableList<List<Person>> allData = this.listContact.getItems();
	      int offset = allData.size();
	      List<String> dataRow = new ArrayList<>();
	      for (int j = 0; j < this.listContact.getColumns().size(); j++) {
	        String mapKey = "Pas de contact"; //Character.toString((char) ('A' + j));
	        //String value1 = mapKey + (offset + 1);
	        dataRow.add(mapKey);//, value1
	      }
	      //allData.addAll(dataRow);
	    }
	
	private void backup() { // enregistrer une bdd au format vcard
		/*File file = new File("my-vcard.vcf");
		VCard vcard = Ezvcard.parse(file).first();
		System.out.println("Name: " + vcard.getFormattedName().getValue());
		System.out.println("Email: " + vcard.getEmails().get(0).getValue());*/
	}
	
	private void export() {
		
	}
	
	@FXML
	public void handleButtonAdd() throws Exception{
		App.setRoot("/isen/projet/view/AddContact");
	}
}
