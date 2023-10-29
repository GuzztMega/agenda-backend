package br.com.guzzmega.agenda.service;

import br.com.guzzmega.agenda.domain.Contact;
import br.com.guzzmega.agenda.domain.Person;
import br.com.guzzmega.agenda.domain.dtos.PersonRecord;
import br.com.guzzmega.agenda.repository.ContactRepository;
import br.com.guzzmega.agenda.repository.PersonRepository;
import br.com.guzzmega.agenda.service.exception.ValidationException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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


}