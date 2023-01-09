package org.example.controllers;

import org.example.dao.PersonDAO;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PersonController {

    private PersonDAO personDAO;

    @Autowired
    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("people", personDAO.showAll());
        return "people/all";
    }
    @GetMapping("/add")
    public String addPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/add";
    }

    @PostMapping("/add")
    public String addPerson(@ModelAttribute("person") @Valid Person person,
                            BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "people/add";
        }
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String personPage(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }
    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model){
        model.addAttribute(personDAO.show(id));
        return "people/edit";
    }
    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("person") @Valid Person person,
                       BindingResult bindingResult,
                       @PathVariable("id") int id){

        if(bindingResult.hasErrors()){
            return "people/edit";
        }

        personDAO.update(id, person);
        return "redirect:/people";
    }
}
