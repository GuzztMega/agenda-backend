package br.com.guzzmega.agenda.domain.builder;

import br.com.guzzmega.agenda.domain.Contact;
import br.com.guzzmega.agenda.domain.Person;

import java.util.UUID;

public class ContactBuilder {

    private UUID idContact;
    private String nickName;
    private String phoneNumber;
    private String email;
    private Person person;

    public Contact build() {
        return new Contact(getIdContact(), getNickName(), getPhoneNumber(), getEmail(), getPerson());
    }

    public static ContactBuilder getOneContact() {
        ContactBuilder builder = new ContactBuilder();
        initializeDefaultValues(builder);
        return builder;
    }

    private static void initializeDefaultValues(ContactBuilder builder) {
        builder.setIdContact(UUID.fromString("dbff6947-17e3-425e-8689-a0c8383773fb"));
        builder.setNickName("johnDoe");
        builder.setPhoneNumber("+5541999887766");
        builder.setEmail("johndoe@gmail.com");
        builder.setPerson(PersonBuilder.getOnePerson().build());
    }

    public ContactBuilder withId(UUID param) {
        this.setIdContact(param);
        return this;
    }

    public ContactBuilder withNickName(String param) {
        this.setNickName(param);
        return this;
    }

    public ContactBuilder withPhoneNumber(String param) {
        this.setPhoneNumber(param);
        return this;
    }

    public ContactBuilder withEmail(String param) {
        this.setEmail(param);
        return this;
    }

    public ContactBuilder withPerson(Person param) {
        this.setPerson(param);
        return this;
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