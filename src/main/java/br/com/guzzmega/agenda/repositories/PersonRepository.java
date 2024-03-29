package br.com.guzzmega.agenda.repositories;

import br.com.guzzmega.agenda.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
}
