package com.epam.tamasknizner.springadvancedtraining.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TODO.
 *
 * @author Tamas_Knizner
 */
@Controller
@RequestMapping("/person")
public class PersonController {

    @RequestMapping(value = {"/add", "/edit"}, method = {RequestMethod.POST})
    public String addPerson(@ModelAttribute("person") Person person) {
//        personService.save(person);
        return "redirect:view?id=" + person.getId();
    }

    @ModelAttribute("person")
    public Person getPerson(@RequestParam(value = "id", required = false) Integer id) {
        Person person = null;
        if (id != null) {
            person = new Person();
            person.id = id;
        }
        return person == null ? new Person() : person;
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String viewPersonForm() {
        return "person/view";
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.GET})
    public String showEditForm(Model model) {
        return "/person/edit";
    }

    private static class Person {
        Integer id;

        public Integer getId() {
            return id;
        }
    }

}
