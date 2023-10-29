package br.com.guzzmega.agenda.models;

import br.com.guzzmega.agenda.dtos.PersonRecord;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "TB_PERSON")
public class Person extends RepresentationModel<Person> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID idPerson;
	private String name;
	private String document;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate birthDate;

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
	private Set<Contact> contacts = new HashSet<>();

	public Person() {
	}

	public Person(PersonRecord personRecord){
		this.name = personRecord.name();
		this.document = personRecord.document();
		this.birthDate = personRecord.birthDate();
		personRecord.contacts().forEach(cr ->
			this.contacts.add(new Contact(cr, this))
		);
	}

	public UUID getIdPerson(){
		return idPerson;
	}

	public void setIdPerson(UUID idPerson){
		this.idPerson = idPerson;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
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

	public Set<Contact> getContacts()	{
		return contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}
}