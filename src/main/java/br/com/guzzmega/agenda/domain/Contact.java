package br.com.guzzmega.agenda.domain;

import br.com.guzzmega.agenda.domain.dtos.ContactRecord;
import br.com.guzzmega.agenda.service.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "TB_CONTACT")
public class Contact extends RepresentationModel<Contact> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idContact;
    private String nickName;
    private String phoneNumber;
    private String email;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Contact() {
    }

    public Contact(@Valid ContactRecord contactRecord, Person person){
        validate(contactRecord.nickName(), contactRecord.phoneNumber(), contactRecord.email());

        this.nickName = contactRecord.nickName();
        this.phoneNumber = contactRecord.phoneNumber();
        this.email = contactRecord.email();
        this.person = person;
    }

    public Contact(UUID idContact, String nickName, String phoneNumber, String email, Person person) {
        validate(nickName, phoneNumber, email);

        this.idContact = idContact;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.person = person;
    }

    private void validate(String nickName, String phoneNumber, String email){
        if(nickName.isEmpty()){
            throw new ValidationException("Contact: Nickname is mandatory");
        }

        if(phoneNumber.isEmpty()){
            throw new ValidationException("Contact: Phone Number is mandatory");
        }

        if(email.isEmpty()){
            throw new ValidationException("Contact: Email is mandatory");
        }

        if(!email.contains("@") || !email.contains(".")){
            throw new ValidationException("Contact: Invalid Email");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact contact)) return false;
        if (!super.equals(o)) return false;
        if(getPerson() != null && contact.getPerson() != null){
            return Objects.equals(getNickName(), contact.getNickName()) && Objects.equals(getPerson(), contact.getPerson());
        } else {
            return Objects.equals(getPhoneNumber(), contact.getPhoneNumber()) && Objects.equals(getEmail(), contact.getEmail());
        }
    }

    @Override
    public String toString() {
        return "Contact{ id=" + idContact + ", nickName='" + nickName + "'" + ", phoneNumber='" + phoneNumber + "'" + ", email='" + email + "' }";
    }

    @Override
    public int hashCode() {
        if(getPerson() != null){
            return Objects.hash(super.hashCode(), getNickName(), getPerson());
        } else {
            return Objects.hash(super.hashCode(), getPhoneNumber(), getEmail());
        }
    }

    public UUID getIdContact() {
        return idContact;
    }

    public void setIdContact(UUID idContact) {
        this.idContact = idContact;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}