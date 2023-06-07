package com.doza.library.services;


import com.doza.library.dao.PersonDAO;
import com.doza.library.models.Book;
import com.doza.library.models.Person;
import com.doza.library.repositories.PersonsRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonsRepository personRepository;
    private final PersonDAO personDAO;

    @Autowired
    public PersonService(PersonsRepository personRepository, PersonDAO personDAO) {
        this.personRepository = personRepository;
        this.personDAO = personDAO;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> findPerson = personRepository.findById(id);
        return findPerson.orElse(null);
    }

    public Optional<Person> getPersonByName(String name) {
        return personRepository.findByName(name);
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatePerson) {
        updatePerson.setId(id);
        personRepository.save(updatePerson);
    }

    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }

    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> person = personRepository.findById(id);

        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            person.get().getBooks().forEach(book ->  {
            long diffInMill = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
            if (diffInMill > 864000000) book.setExpired(true);
            });
            return person.get().getBooks();
        } else {
            return Collections.emptyList();
        }
    }
}
