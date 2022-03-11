package isen.projet.db.daos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import isen.projet.db.entities.Person;

public class PersonTestDao {
	private PersonDao personDao = new PersonDao();
	
	@Before
	public void initDb() throws Exception {
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
	
	@Test
	public void shouldListPerson() throws Exception {
		//WHEN
		List<Person> persons = personDao.listPersons();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		
		//THEN
		assertThat(persons).hasSize(3);
		assertThat(persons).extracting("idPerson", "lastName", "firstName", "nickName", "phoneNumber", "adress", "emailAdress","birthDate").containsOnly(
				tuple(1,"Last Name 1", "First name 1", "Nick Name 1", "0601010101", "address 1", "email 1", format.parse("2001-01-10 11:00:00.000")),
				tuple(2,"Last Name 2", "First name 2", "Nick Name 2", "0602020202", "address 2", "email 2", format.parse("2002-02-20 22:00:00.000")),
				tuple(3,"Last Name 3", "First name 3", "Nick Name 3", "0603030303", "address 3", "email 3", format.parse("2003-03-30 00:30:00.000")));
	}
	
	@Test
	public void shouldAddPerson() throws Exception{
		// WHEN 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		Date date = new Date(format.parse("2004-04-14 04:40:00.000").getTime());
		Person person = new Person(5,"Last Name 4", "First Name 4", "Nick Name 4", "0604040404", "address 4", "email 4", date);
		personDao.addPerson(person);

		// THEN
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE lastname='Last Name 4'");
		assertThat(resultSet.next()).isTrue();
		assertThat(resultSet.getInt("idperson")).isNotNull();
		assertThat(resultSet.getString("lastname")).isEqualTo("Last Name 4");
		assertThat(resultSet.getString("firstname")).isEqualTo("First Name 4");
		assertThat(resultSet.getString("nickname")).isEqualTo("Nick Name 4");
		assertThat(resultSet.getString("phone_number")).isEqualTo("0604040404");
		assertThat(resultSet.getString("address")).isEqualTo("address 4");
		assertThat(resultSet.getString("email_address")).isEqualTo("email 4");
		assertThat(resultSet.getDate("birth_date")).isEqualTo(date);
		assertThat(resultSet.next()).isFalse();
		resultSet.close();
		statement.close();
		connection.close();
	}
	
	@Test
	public void shouldUpdatePerson() throws Exception {
		//WHEN
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		Date date = new Date(format.parse("2000-01-01 00:00:00.000").getTime());
		Person person = new Person(2,"Last Name 0", "First Name 0", "Nick Name 0", "0600000000", "address 0", "email 0", date);
		personDao.updatePerson(person);
		
		// THEN
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE idperson=2");
		assertThat(resultSet.next()).isTrue();
		assertThat(resultSet.getInt("idperson")).isEqualTo(2);
		assertThat(resultSet.getString("lastname")).isEqualTo("Last Name 0");
		assertThat(resultSet.getString("firstname")).isEqualTo("First Name 0");
		assertThat(resultSet.getString("nickname")).isEqualTo("Nick Name 0");
		assertThat(resultSet.getString("phone_number")).isEqualTo("0600000000");
		assertThat(resultSet.getString("address")).isEqualTo("address 0");
		assertThat(resultSet.getString("email_address")).isEqualTo("email 0");
		assertThat(resultSet.getDate("birth_date")).isEqualTo(date);
		assertThat(resultSet.next()).isFalse();
		resultSet.close();
		statement.close();
		connection.close();
	}
	
	@Test
	public void shouldDeletePerson() throws Exception{
		//WHEN
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		Date date = new Date(format.parse("2001-01-10 11:00:00.000").getTime());
		Person person = new Person(1,"Last Name 1", "First Name 1", "Nick Name 1", "0601010101", "address 1", "email 1", date);

		personDao.deletePerson(1);
		List<Person> persons = personDao.listPersons();
		
		//THEN
		assertThat(persons).hasSize(2);
		assertThat(persons).extracting("idPerson", "lastName").doesNotContain(tuple(1, "Last Name 1"));//.containsOnly(tuple(2, "Last Name 2"), tuple(3, "Last Name 3")
	}
	
	@After
	public void exportDB() throws Exception{
		personDao.export();
	}
}