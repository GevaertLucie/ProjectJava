package isen.projet.db.daos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import isen.projet.db.entities.Person;

public class PersonDao {

	private static DataSource getDataSource() {
		return DataSourceFactory.getDataSource();
	}

	public List<Person> listPersons() {
		List<Person> listPerson = new ArrayList<>();
		try (Connection connection = getDataSource().getConnection()) {
			try (Statement statement = connection.createStatement()) {
				try (ResultSet results = statement.executeQuery("SELECT * FROM person")) {
					while (results.next()) {
						Person person = new Person(results.getInt("idperson"),
								results.getString("lastname"),
								results.getString("firstname"),
								results.getString("nickname"),
								results.getString("phone_number"),
								results.getString("address"),
								results.getString("email_address"),
								results.getDate("birth_date"));
						listPerson.add(person);
					}
					results.close();
				}
				statement.close();
			}
			connection.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return listPerson;
	}

	public Person addPerson(Person person) {
		try (Connection connection = getDataSource().getConnection()) {
			String sqlQuery = "INSERT INTO person(lastname, firstname, nickname, phone_number, address, email_address, birth_date) VALUES(?,?,?,?,?,?,?)";
			try (PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, person.getLastName());
				statement.setString(2, person.getFirstName());
				statement.setString(3, person.getNickName());
				statement.setString(4, person.getPhoneNumber());
				statement.setString(5, person.getAdress());
				statement.setString(6, person.getEmailAdress());
				statement.setDate(7, person.getBirthDate());
				statement.executeUpdate();

				ResultSet ids = statement.getGeneratedKeys();
				if (ids.next()) {
					Person p = new Person(ids.getInt(1),
							person.getLastName(),
							person.getFirstName(),
							person.getNickName(),
							person.getPhoneNumber(),
							person.getAdress(),
							person.getEmailAdress(),
							person.getBirthDate());
					return(p);
				}
				statement.close();
			}
			connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Person updatePerson(Person person) {
		try (Connection connection = getDataSource().getConnection()) {
			String sqlQuery = "UPDATE person SET lastname=?, firstname=?, nickname=?, phone_number=?, address=?, email_address=?, birth_date=? WHERE idperson=?";
			try (PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, person.getLastName());
				statement.setString(2, person.getFirstName());
				statement.setString(3, person.getNickName());
				statement.setString(4, person.getPhoneNumber());
				statement.setString(5, person.getAdress());
				statement.setString(6, person.getEmailAdress());
				statement.setDate(7, person.getBirthDate());
				statement.setInt(8, person.getIdPerson());
				statement.executeUpdate();

				statement.close();
			}
			connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return person;
	}

	public void deletePerson(Person person) {
		try (Connection connection = getDataSource().getConnection()) {
			String sqlQuery = "DELETE FROM person WHERE idperson=?";
			try (PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
				statement.setInt(1, person.getIdPerson());
				statement.executeUpdate();

				statement.close();
			}
			connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void export(List<Person> listPerson) {
		for(Person p : listPerson) {
			try {
				File f = new File(p.getFirstName()+".vcf");
				if(!f.exists()) {
					if (f.createNewFile()) {
						System.out.println(f.getName() + " File created");
					}
					else {
						System.err.println("Création du fichier échoué");
					}
				}

				try {
					FileWriter writer = new FileWriter(f);
					writer.write("BEGIN:VCARD\n");
					writer.write("VERSION:3.0\n");
					writer.write("N:"+p.getLastName()+";"+p.getFirstName()+"\n");
					writer.write("FN:"+p.getLastName()+" "+p.getFirstName()+"\n");
					writer.write("NICKNAME:"+p.getNickName()+"\n");
					writer.write("TEL;HOME;VOICE:"+p.getPhoneNumber()+"\n");
					writer.write("ADR;HOME:;;"+p.getAdress()+"\n");
					writer.write("EMAIL:"+p.getEmailAdress()+"\n");
					writer.write("BDAY:"+p.getBirthDate()+"\n");
					writer.write("END:VCARD");
					writer.close();
				}
				catch (IOException e) {
					System.err.println("Erreur d'exportation !"+ p.getFirstName());
				}
			}
			catch (Exception e) {
				System.err.println(e);
			}
		}
	}
}