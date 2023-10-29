package br.com.guzzmega.agenda.domain;

import br.com.guzzmega.agenda.domain.builder.ContactBuilder;
import br.com.guzzmega.agenda.domain.builder.PersonBuilder;
import br.com.guzzmega.agenda.service.exception.ValidationException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Tag("domain")
@Tag("person")
@DisplayName("Domain: Person")
public class PersonTest {

    private static final UUID PERSON_ID = UUID.fromString("1e773fdf-71ef-4717-8341-f231b3917c35");
    private static final UUID CONTACT_ID = UUID.fromString("dbff6947-17e3-425e-8689-a0c8383773fb");
	@BeforeAll
	public static void setupAll(){
		System.out.println("<-- STARTING -->");
	}

	@AfterAll
	public static void teardownAll(){
		System.out.println("<--  ENDING  -->");
	}

	@Test
	@DisplayName("Must Create a Valid Person")
	public void mustCreateValidPerson(){

		Person person = PersonBuilder.getOnePerson().build();
        Contact contact = ContactBuilder.getOneContact().build();
		person.getContacts().add(contact);

		Set<Contact> contacts = new HashSet<>();
		contacts.add(ContactBuilder.getOneContact().build());

		assertAll(
				"Create Valid Person",
				() -> assertEquals(PERSON_ID, person.getIdPerson()),
				() -> assertEquals("João", person.getName()),
				() -> assertEquals("91038891060", person.getDocument()),
				() -> assertEquals(LocalDate.of(1991, 5, 5), person.getBirthDate()),
				() -> assertEquals(	contacts, person.getContacts())
		);
	}

    @DisplayName("Must Reject a Person Without Field")
    @ParameterizedTest(name = "{index}. {4}")
    @CsvFileSource(
            files = "src/test/resources/mustRejectPersonWithoutField.csv",
            numLinesToSkip = 1,
            nullValues = "NULL",
            delimiter = ';'
    )
    public void mustRejectPersonWithoutField(String name, String document, LocalDate birthDate, String message) {
		Set<Contact> contacts = new HashSet<>();
		contacts.add(ContactBuilder.getOneContact().build());

        ValidationException exception = assertThrows(ValidationException.class, () ->
			PersonBuilder.getOnePerson().withId(PERSON_ID).withName(name).withDocument(document).withBirthDate(birthDate).withContacts(contacts).build()
		);
        assertEquals(message, exception.getMessage());
    }
}