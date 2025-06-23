package com.Jayesh.SplitApp.controller;

import com.Jayesh.SplitApp.model.Person;
import com.Jayesh.SplitApp.repository.PersonRepository;
import com.Jayesh.SplitApp.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@CrossOrigin
@Controller
@RequestMapping("/people")
@RequiredArgsConstructor
public class PersonController {

    @Autowired
    private final PersonService personService;


    @GetMapping
    public ResponseEntity<List<Person>> findAll() {
        return ResponseEntity.ok(personService.getAllPeople());
    }

}
