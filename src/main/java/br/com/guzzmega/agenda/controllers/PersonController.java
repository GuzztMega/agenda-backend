package br.com.guzzmega.agenda.controllers;

import br.com.guzzmega.agenda.dtos.PersonRecord;
import br.com.guzzmega.agenda.models.Person;
import br.com.guzzmega.agenda.services.PersonService;
import br.com.guzzmega.agenda.services.exceptions.ValidationException;
import jakarta.validation.Valid;
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
    private PersonService personService;

	@PostMapping("/persons")
	public ResponseEntity<Object> insert(@RequestBody @Valid PersonRecord personRecord){
		try	{
			return ResponseEntity.status(HttpStatus.CREATED).body(personService.save(personRecord));
		} catch (ValidationException validationException) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationException.getMessage());
		}
	}

	@GetMapping("/persons")
	public ResponseEntity<List<Person>> getAll(){
		List<Person> personList = personService.findAll();

		if(!personList.isEmpty()){
			for(Person person : personList){
				person.add(linkTo(methodOn(PersonController.class).getOne(person.getIdPerson())).withSelfRel());
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(personList);
	}

	@GetMapping("/persons/{id}")
	public ResponseEntity<Object> getOne(@PathVariable(value="id") UUID id){
		Optional<Person> personOptional = personService.findById(id);

		if(personOptional.isEmpty()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Can't find person with ID %s", id));
		}

		personOptional.get().add(linkTo(methodOn(PersonController.class).getAll()).withRel("personList"));
		return ResponseEntity.status(HttpStatus.OK).body(personOptional.get());
	}

	@PutMapping("/persons/{id}")
	public ResponseEntity<Object> update(@RequestBody @Valid PersonRecord personRecord, @PathVariable(value="id" ) UUID id){
		try	{
			Optional<Person> personOptional = personService.findById(id);
			if(personOptional.isEmpty()){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Can't find person with ID %s", id));
			}

			return ResponseEntity.status(HttpStatus.OK).body(personService.update(personRecord, personOptional.get()));
		} catch (ValidationException validationException) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationException.getMessage());
		}
	}

	@DeleteMapping("/persons/{id}")
	public ResponseEntity<Object> delete(@PathVariable(value="id") UUID id){
		if(!personService.delete(id)){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Can't find person with ID %s", id));
		}

		return ResponseEntity.status(HttpStatus.OK).body("Person deleted successfully!");
	}
}