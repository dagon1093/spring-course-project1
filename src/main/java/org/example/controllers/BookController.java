package org.example.controllers;

import org.example.dao.BookDAO;
import org.example.dao.PersonDAO;
import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String showAll(Model model){
        model.addAttribute("books", bookDAO.showAll());
        return "books/all";
    }

    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String newBook(@ModelAttribute("book") @Valid Book book,
                          BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id,
                       @ModelAttribute("person")Person person,
                       Model model){
        Optional<Person> bookOwner = bookDAO.getBookOwner(id);
        if (bookOwner.isPresent()){
            model.addAttribute("owner", bookOwner.get());
        } else {
            model.addAttribute("people", personDAO.showAll());
        }

        model.addAttribute("book", bookDAO.show(id));
//        model.addAttribute("person", new Person());
        return "books/show";
    }

    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("person") Person person, @PathVariable("id") Integer id){
        Book book = bookDAO.show(id);
        book.setPerson_id(person.getId());
        bookDAO.update(book.getId(),book);
        return "redirect:/books/" + id;
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable("id") Integer id){
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editBook(@ModelAttribute("book") Book book, @PathVariable("id") Integer id){
        bookDAO.update(id, book);
        return "redirect:/books";
    }
}
