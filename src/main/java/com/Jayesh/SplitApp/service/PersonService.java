package com.Jayesh.SplitApp.service;

import com.Jayesh.SplitApp.model.Person;
import com.Jayesh.SplitApp.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    @Autowired
    private final PersonRepository personRepository;

    public List<Person> getAllPeople() {
        return personRepository.findAll();

    }
}
