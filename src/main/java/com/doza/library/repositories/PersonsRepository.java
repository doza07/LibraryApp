package com.doza.library.repositories;


import com.doza.library.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PersonsRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByName(String name);
}
