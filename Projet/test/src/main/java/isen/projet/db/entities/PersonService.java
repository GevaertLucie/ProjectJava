package isen.projet.db.entities;

import isen.projet.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersonService {

	private ObservableList<Person> contacts;

	private PersonService() {
		contacts = FXCollections.observableArrayList();
		contacts.addAll(App.personDao.listPersons());
	}


	public static ObservableList<Person> getPersons() {
		return PersonServiceHolder.INSTANCE.contacts;
	}

	public static void addPerson(Person person) {
		PersonServiceHolder.INSTANCE.contacts.add(person);
	}

	private static class PersonServiceHolder {
		private static final PersonService INSTANCE = new PersonService();
	}
}
