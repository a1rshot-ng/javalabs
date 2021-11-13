package org.airng.labthird.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.airng.labthird.dao.CachedPeopleDao;
import org.airng.labthird.dao.Dao;
import org.airng.labthird.service.PeopleFileReader;
import org.airng.labthird.service.PeopleService;
import org.airng.labthird.service.UpdateCmd;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

@SpringBootApplication
@RestController
public class Application {
	private PeopleService peopleService = new PeopleService();

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@RequestMapping (value = "/")
	public String status() {
		return "Server online.";
	}

	@RequestMapping (value = "/new/teacher", method = RequestMethod.POST)
	public String reqAddTeacher(@RequestBody Teacher teacher) {
		UUID id = peopleService.createTeacher(teacher.getFullName(), teacher.getBirthDate(), teacher.getPhoneNumber(), teacher.subject, teacher.workTime);
		if (id != null)
			return "Created teacher record: " + id;
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping (value = "/new/student", method = RequestMethod.POST)
	public String reqAddStudent(@RequestBody Student student) {
		UUID id = peopleService.createStudent(student.getFullName(), student.getBirthDate(), student.getPhoneNumber(), student.subjects, student.marks);
		if (id != null)
			return "Created student record: " + id;
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping (value = "/get")
	public Person reqGet(@RequestParam UUID id) {
		Person person = peopleService.getPerson(id);
		if (person != null)
			return person;
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping (value = "/delete", method = RequestMethod.POST)
	public String reqDel(@RequestBody UUID id) {
		try {
			peopleService.deletePerson(id);
			return "Person deleted: " + id;
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping (value = "/update", method = RequestMethod.POST)
	public String reqUpd(@RequestBody UpdateCmd cmd) {
		UUID id = peopleService.updatePerson(cmd);
		if (id != null)
			return "Updated person record: " + id;
		return "Error: could not process update command";
	}

	@RequestMapping (value = "/read")
	public String reqRead(@RequestParam String file) {
		PeopleFileReader peopleFileReader = PeopleFileReader.getInstance();
		try {
			Dao<Person> dao = peopleFileReader.ReadFromFile(file);
			peopleService = new PeopleService(dao);
			return "Database loaded from file '" + file + "'.";
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	// for reader test & demo
	@RequestMapping (value = "/save")
	public String reqSave(@RequestParam String file) {
		try {
			Dao<Person> dao = peopleService.getDao();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(new File(file), dao);
			return "Database saved to file '" + file + "'";
		} catch (IOException e) {
			System.err.println(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}
}
