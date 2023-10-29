package br.com.guzzmega.agenda.domain.builder;

import br.com.guzzmega.agenda.domain.Contact;
import br.com.guzzmega.agenda.domain.Person;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PersonBuilder {
	private UUID idPerson;
	private String name;
	private String document;
	private LocalDate birthDate;
	private Set<Contact> contacts;

	public Person build() {
		return new Person(getIdPerson(), getName(), getDocument(), getBirthDate(), getContacts());
	}

	public static PersonBuilder getOnePerson() {
		PersonBuilder builder = new PersonBuilder();
		initializeDefaultValues(builder);
		return builder;
	}

	private static void initializeDefaultValues(PersonBuilder builder) {
		builder.setIdPerson(UUID.fromString("1e773fdf-71ef-4717-8341-f231b3917c35"));
		builder.setName("João");
		builder.setDocument("91038891060");
		builder.setBirthDate(LocalDate.of(1991, 5, 5));
		builder.setContacts(new HashSet<>());
	}

	public PersonBuilder withId(UUID param) {
		this.setIdPerson(param);
		return this;
	}

	public PersonBuilder withName(String param) {
		this.setName(param);
		return this;
	}

	public PersonBuilder withDocument(String param) {
		this.setDocument(param);
		return this;
	}

	public PersonBuilder withBirthDate(LocalDate param) {
		this.setBirthDate(param);
		return this;
	}

	public PersonBuilder withContacts(Set<Contact> param) {
		this.setContacts(param);
		return this;
	}

	public UUID getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(UUID idPerson) {
		this.idPerson = idPerson;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}
}