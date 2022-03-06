package isen.projet.db.entities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import isen.projet.db.daos.DataSourceFactory;

public class PersonService {
	
	private List<Person> questions;
	
	private PersonService() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		questions = new ArrayList<>();
		//questions.add(new Person(1,"Last Name 1", "First name 1", "Nick Name 1", "0601010101", "address 1", "email 1", new Date(format.parse("2001-01-10 11:00:00.000").getTime())));
		//questions.add(new Person(2,"Last Name 2", "First name 2", "Nick Name 2", "0602020202", "address 2", "email 2", new Date(format.parse("2002-02-20 22:00:00.000").getTime())));
		//questions.add(new Person(3,"Last Name 3", "First name 3", "Nick Name 3", "0603030303", "address 3", "email 3", new Date(format.parse("2003-03-30 03:00:00.000").getTime())));
		//questions.add(new Person(4,"Last Name 4", "First name 4", "Nick Name 1", "0601010101", "address 1", "email 1", new Date(format.parse("2001-01-10 11:00:00.000").getTime())));
		//public void initDb() throws Exception {
			/*Connection connection = DataSourceFactory.getDataSource().getConnection();
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
			connection.close();*/
		//}
	}
}
