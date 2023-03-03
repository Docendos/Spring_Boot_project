package com.library.demo.controllers;


import com.library.demo.models.Book;
import com.library.demo.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class LibraryController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/Library")
    public String libraryMain(Model model){
        Iterable<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "library-main";
    }

    @GetMapping("/Library/add")
    public String bookAdd(Model model){
        return "book-add";
    }

    @PostMapping("/Library/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons, Model model){
        Book book = new Book(title, anons);
        bookRepository.save(book);
        return "redirect:/Library";
    }

    @GetMapping("/Library/{id}")
    public String bookDetails(@PathVariable(value = "id") long id, Model model){
        if(!bookRepository.existsById(id)){
            return "redirect:/Library";
        }
        Optional<Book> book =  bookRepository.findById(id);
        ArrayList<Book> res = new ArrayList<>();
        book.ifPresent(res::add);
        model.addAttribute("book", res);
        return "book-details";
    }

    @GetMapping("/Library/{id}/edit")
    public String bookEdit(@PathVariable(value = "id") long id, Model model){
        if(!bookRepository.existsById(id)){
            return "redirect:/Library";
        }
        Optional<Book> book =  bookRepository.findById(id);
        ArrayList<Book> res = new ArrayList<>();
        book.ifPresent(res::add);
        model.addAttribute("book", res);
        return "book-edit";
    }

    @PostMapping("/Library/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, Model model){
        Book book = bookRepository.findById(id).orElseThrow();
        book.setTitle(title);
        book.setAnons(anons);
        bookRepository.save(book);
        return "redirect:/Library";
    }

    @PostMapping("/Library/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model){
        Book book = bookRepository.findById(id).orElseThrow();
        bookRepository.delete(book);
        return "redirect:/Library";
    }
}
