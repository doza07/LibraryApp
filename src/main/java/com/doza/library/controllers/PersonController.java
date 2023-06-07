package com.doza.library.controllers;

import com.doza.library.models.Person;
import com.doza.library.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public String listPerson(Model model) {
        model.addAttribute("person", personService.findAll());
        return "person/list";
    }


    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.findOne(id));
        model.addAttribute("book", personService.getBooksByPersonId(id));
        return "person/show";

    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "person/new";
    }

    @PostMapping
    public String createPerson(@ModelAttribute("person")@Valid  Person person, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "person/new";
        personService.save(person);
        return "redirect:/person";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personService.findOne(id));
        return "person/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person")@Valid Person person, BindingResult bindingResult,
                             @PathVariable("id") int id) {
        if(bindingResult.hasErrors())
            return "person/edit";
        personService.update(id, person);
        return "redirect:/person";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personService.delete(id);
        return "redirect:/person";
    }


}
