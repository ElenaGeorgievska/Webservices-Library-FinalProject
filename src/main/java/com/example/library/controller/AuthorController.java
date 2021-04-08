package com.example.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import com.example.library.services.AuthorService;


@Controller
public class AuthorController {

	@Autowired
	AuthorRepository authorRepo;

    @Autowired
    BookRepository bookRepo;
    
    @Autowired
    AuthorService authorService;

    
	// get all authors
	/*@GetMapping("/getAllAuthors")
	public String viewAuthors(Model model) {
		

		List<Author> listAuthors = authorRepo.findAll(); // get list of authors
		model.addAttribute("listAuthors", listAuthors);
		
		return findPaginatedHome(1, "firstName", "asc", model);
		//return "authors/authorsList_home";

	}*/
    
    
    // List of authors, Search
	@GetMapping("/authors")
	public String viewAuthorsHome(Model model,@Param("keyword") String keyword) {
				
		findPaginatedHome(1, "firstName", "asc", model);
		
		List<Author> listAuthors = authorService.searchAuthors(keyword);
		model.addAttribute("listAuthors", listAuthors);
		model.addAttribute("keyword", keyword);
		
		return "authors/authorsList_home";
		
	   	
	}
	

	// create author
	@PostMapping("/authors/add")
	public String createAuthor(@ModelAttribute("author") Author author) {

		authorRepo.save(author);

		return "authors/authorDetails";
		// return "redirect:/authors";

	}

	// new author form
	@GetMapping("/authors/showNewAuthorForm")
	public String showNewAuthorForm(Model model) {

		Author author = new Author(); // creating object for the form
		model.addAttribute("author", author);

		List<Book> listBooks = bookRepo.findAll(); // get list of books
		model.addAttribute("listBooks", listBooks);

		return "authors/new_authorForm";
	}

	// show form for update author
	@GetMapping("/authors/showUpdateAuthorForm/{id}")
	public String showUpdateAuthorForm(@PathVariable(value = "id") Integer id, Model model) {

		Author author = authorRepo.findById(id).get();
		model.addAttribute("author", author);

		List<Book> listBooks = bookRepo.findAll(); // get list of Books
		model.addAttribute("listBooks", listBooks);

		return "authors/updateAuthor";
	}
	

	// update author
	@PostMapping("/authors/updateAuthor")
	public String updateAuthor(@ModelAttribute("author") Author author) {

		authorRepo.save(author);

		// return "authors/authorDetails";
		return "redirect:/authors";

	}

	// view details for Author
	@GetMapping("/authors/viewAuthorDetails/{id}")
	public String viewAuthorDetails(@PathVariable(value = "id") Integer id, Model model) {

		Author author = authorRepo.findById(id).get();
		model.addAttribute(author);

		return "authors/authorDetails";

	}

	// delete Author
	@GetMapping("/authors/deleteAuthor/{id}")
	public String deleteAuthor(@PathVariable("id") Integer id) {
		authorRepo.deleteById(id);

		return "redirect:/authors";

	}
	
	
	//paging and sorting
		@GetMapping("/authors/page/{pageNo}")
		public String findPaginatedHome(@PathVariable (value = "pageNo") Integer pageNo, 
				@RequestParam("sortField") String sortField,
				@RequestParam("sortDir") String sortDir,
				Model model) {
			Integer pageSize = 5;
			
			Page<Author> page = authorService.findPaginated(pageNo, pageSize, sortField, sortDir);
			List<Author> listAuthors = page.getContent();
			
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());
			
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDir", sortDir);
			model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
			
			model.addAttribute("listAuthors", listAuthors);
			
			
			return "authors/authorsList_home";
		}
}
