package br.com.guzzmega.agenda.domain;

import br.com.guzzmega.agenda.domain.dtos.PersonRecord;
import br.com.guzzmega.agenda.service.exception.ValidationException;
import br.com.guzzmega.agenda.utils.DocumentUtils;
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
		Set<Contact> contactSet = new HashSet<>();
		personRecord.contacts().forEach(cr ->
				contactSet.add(new Contact(cr, this))
		);

		this.validate(personRecord.name(),personRecord.document(),personRecord.birthDate(), contactSet);

		this.name = personRecord.name();
		this.document = personRecord.document();
		this.birthDate = personRecord.birthDate();
		this.contacts = contactSet;
	}

	public Person(UUID idPerson, String name, String document, LocalDate birthDate, Set<Contact> contacts) {
		this.validate(name,document,birthDate,contacts);

		this.idPerson = idPerson;
		this.name = name;
		this.document = document;
		this.birthDate = birthDate;
		this.contacts = contacts;
	}

	@Override
	public String toString() {
		return "Person{ id=" + idPerson + ", name='" + name + "'" + ", document='" + document + "'" + ", birthDate=" + birthDate + ", contacts=" + contacts + '}';
	}

	private void validate(String name, String document, LocalDate birthDate, Set<Contact> contacts){
		if(!DocumentUtils.isValidCPF(document))        {
			throw new ValidationException("Document: Invalid CPF");
		}

		if(birthDate.isAfter(LocalDate.now())){
			throw new ValidationException("BirthDate: can't be in the future");
		}

		if(name.isEmpty()){
			throw new ValidationException("Name is mandatory");
		}

		if(contacts.isEmpty()){
			throw new ValidationException("Contacts: must have at least one Contact");
		}
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