package br.com.guzzmega.agenda.controllers;

import br.com.guzzmega.agenda.dtos.PersonRecord;
import br.com.guzzmega.agenda.models.PersonModel;
import br.com.guzzmega.agenda.repositories.PersonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PersonController {

	@Autowired
    PersonRepository personRepository;

	@PostMapping("/persons")
	public ResponseEntity<PersonModel> savePerson(@RequestBody @Valid PersonRecord personRecord){
		var personModel = new PersonModel();
		BeanUtils.copyProperties(personRecord, personModel);

		return ResponseEntity.status(HttpStatus.CREATED).body(personRepository.save(personModel));
	}

	@GetMapping("/persons")
	public ResponseEntity<List<PersonModel>> getAllPersons(){
		List<PersonModel> personList = personRepository.findAll();

		if(!personList.isEmpty()){
			for(PersonModel person : personList){
				person.add(linkTo(methodOn(PersonController.class).getOnePerson(person.getIdPerson())).withSelfRel());
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(personList);
	}

	@GetMapping("/persons/{id}")
	public ResponseEntity<Object> getOnePerson(@PathVariable(value="id") UUID id){
		Optional<PersonModel> personOptional = personRepository.findById(id);

		if(personOptional.isEmpty()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Can't find person with ID %s", id));
		}

		personOptional.get().add(linkTo(methodOn(PersonController.class).getAllPersons()).withRel("personList"));
		return ResponseEntity.status(HttpStatus.OK).body(personOptional.get());
	}

	@PutMapping("/persons/{id}")
	public ResponseEntity<Object> putPerson(@RequestBody @Valid PersonRecord personRecord, @PathVariable(value="id" ) UUID id){
		Optional<PersonModel> personOptional = personRepository.findById(id);

		if(personOptional.isEmpty()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Can't find person with ID %s", id));
		}

		BeanUtils.copyProperties(personRecord, personOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body(personRepository.save(personOptional.get()));
	}

	@DeleteMapping("/persons/{id}")
	public ResponseEntity<Object> deleteModel(@PathVariable(value="id") UUID id){
		Optional<PersonModel> personOptional = personRepository.findById(id);

		if(personOptional.isEmpty()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Can't find person with ID %s", id));
		}

		personRepository.delete(personOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Person deleted successfully!");
	}
}