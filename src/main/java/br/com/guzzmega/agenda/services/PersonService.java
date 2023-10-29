package br.com.guzzmega.agenda.services;

import br.com.guzzmega.agenda.dtos.PersonRecord;
import br.com.guzzmega.agenda.models.Contact;
import br.com.guzzmega.agenda.models.Person;
import br.com.guzzmega.agenda.repositories.ContactRepository;
import br.com.guzzmega.agenda.repositories.PersonRepository;
import br.com.guzzmega.agenda.services.exceptions.ValidationException;
import br.com.guzzmega.agenda.utils.DocumentUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ContactRepository contactRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Optional<Person> findById(UUID id) {
        return personRepository.findById(id);
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    @Transactional
    public Person save(PersonRecord personRecord) {
        return this.save(new Person(personRecord));
    }

    @Transactional
    public Person save(Person person) throws ValidationException {
        if(!DocumentUtils.isValidCPF(person.getDocument()))        {
            throw new ValidationException("Document: Invalid CPF");
        }

        if(person.getBirthDate().isAfter(LocalDate.now())){
            throw new ValidationException("BirthDate: can't be in the future");
        }

        if(person.getContacts().isEmpty()){
            throw new ValidationException("Contacts: must have at least one Contact");
        } else {
            person.getContacts().forEach(this::validateContact);
        }

        Person savedPerson = personRepository.save(person);
        contactRepository.saveAll(savedPerson.getContacts());
        return savedPerson;
    }

    @Transactional
    public Person update(PersonRecord personRecord, Person person){
        BeanUtils.copyProperties(personRecord, person);

        contactRepository.deleteAll(person.getContacts());
        person.setContacts(new HashSet<>());
        personRecord.contacts().forEach(cr ->
                person.getContacts().add(new Contact(cr, person))
        );

        return this.save(person);
    }

    public boolean delete(UUID id){
        Optional<Person> personOptional = personRepository.findById(id);

        if(personOptional.isPresent()){
            personRepository.deleteById(personOptional.get().getIdPerson());
            return true;
        }

        return false;
    }

    private void validateContact(Contact contact){
        if(contact.getNickName().isEmpty()){
            throw new ValidationException("Contact: Nickname is mandatory");
        }

        if(contact.getPhoneNumber().isEmpty()){
            throw new ValidationException("Contact: Phone Number is mandatory");
        }

        if(contact.getEmail().isEmpty()){
            throw new ValidationException("Contact: Email is mandatory");
        }

        if(!contact.getEmail().contains("@") || !contact.getEmail().contains(".")){
            throw new ValidationException("Contact: Invalid Email");
        }
    }
}