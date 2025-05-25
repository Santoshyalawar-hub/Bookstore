package com.bookStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bookStore.entity.Book;
import com.bookStore.entity.MyBookList;
import com.bookStore.service.BookService;
import com.bookStore.service.MyBookListService;

@Controller
public class BookController {
	@Autowired
	private BookService service;
	
	@Autowired
	private MyBookListService myBookService;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/book_register")
	public String bookRegister() {
		return "bookRegister";
	}
	@GetMapping("/available_books")
	public ModelAndView getAllBook() {
		List<Book> list = service.getAllBooks();
		return new ModelAndView("bookList","book",list);
	}
	
	@PostMapping("/save")
	public String addBook(@ModelAttribute Book b) {
		service.save(b);
		return "redirect:/available_books";
	}
	
	@GetMapping("/my_books")
	public String getmyBook(Model model) {
		List<MyBookList> list = myBookService.getAllMyBook();
		model.addAttribute("book",list);
		return "myBooks";
	}
	@RequestMapping("/myList/{id}")
	public String getMyList( @PathVariable("id") int id) {
		Book b = service.getBookId(id);
		MyBookList mb = new MyBookList(b.getId(),b.getName(),b.getAuthor(),b.getPrice());
		myBookService.saveMyBook(mb);
		return "redirect:/my_books";
	}
	@RequestMapping("/editBook/{id}")
	public String editBook(@PathVariable("id")int id, Model model) {
		Book b = service.getBookId(id);
		model.addAttribute("book",b);
		return "bookEdit";
	}
	@RequestMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id") int id) {
		service.deleteById(id);
		return "redirect:/available_books";
	}

}
