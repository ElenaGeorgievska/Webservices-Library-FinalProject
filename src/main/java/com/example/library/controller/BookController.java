package com.example.library.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.library.FileUploadUtil;
import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.entity.BookDepartment;
import com.example.library.entity.Loan;
import com.example.library.entity.Member;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookDepartmentRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.services.BookService;


@Controller
public class BookController {
	
	@Autowired
	AuthorRepository authorRepo;

    @Autowired
    BookRepository bookRepo;
    
    @Autowired
    BookDepartmentRepository bookDeptRepo;
    
    @Autowired
    LoanRepository loanRepo;
    
    @Autowired
	private BookService bookService;
    
    
    @PostMapping("/books/upload")
    public String uploadFile(Book book, @RequestParam("image") MultipartFile multipartFile) throws IOException {
	    
	 
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        book.setPhoto(fileName);
         
        Book savedBook = bookRepo.save(book);
 
        String uploadDir = "./uploadBookPhoto/" + savedBook.getId();
 
        FileUploadUtil.uploadFile(uploadDir, fileName, multipartFile);
       
        
        bookRepo.save(book);
        
       return "redirect:/books";
      
    }

    
    
  //find book page -Home page for book
  	@GetMapping("/books/findBook")
  	public String findOwnersPage(Model model) {
  		Book book = new Book();
  		model.addAttribute("book", book);
  		return "books/findBooks";
  	}
    
  	
  //search book by name
  	@GetMapping("/books/searchBook")  
  	public String searchBook(Model model, Book book) {
  		
  		
  		
  	    //return all records possible in database table when we leave empty field for name
  		if (book.getName() == (String) null) {
  			book.setName("");
  		}
  		
  	    //return all records possible in database table when we leave empty field for name
  		/*if (book.getName().equals("")) {
  			List<Book> listBooks = bookRepo.findAll();
  			model.addAttribute("listBooks", listBooks);
  			return "books/booksList_home";
  		}*/
  		
  		
  		// find books by name
  		List<Book> resultsBooks = bookRepo.searchBook(book.getName());
  		//List<Book> resultsBooks = bookRepo.searchBook1(keyword);
  		
  		//findPaginated(1, "name", "asc", model);
  		
  		if (resultsBooks.isEmpty()) {        //if no books are found after typing a name
  			// no books found
  			List<Book> listBooks = bookRepo.findAll();
  			model.addAttribute("listBooks", listBooks);
  			return "redirect:/books/findBook";
  			
  		}
  		else if (resultsBooks.size() == 1) {
  			// 1 book found
  			book = resultsBooks.iterator().next();     
  		    return "redirect:/books/viewBookDetails/" + book.getId();                     //Here we use display owners info method --> /books/viewBookDetails/{id}  kade {id} vo nasiot slucaj e book.getId();
  			//return "redirect:/books/viewBookDetails/" + resultsBooks.get(0).getId();   // ovde e primenet metodot /books/viewBookDetails/{id} kade za id imame + resultsBooks.get(0).getId()
  					
  		}
  		else {
  			// multiple owners found
  			findPaginated(1, "name", "asc", model);
  			model.addAttribute("listBooks", resultsBooks);
  			
  			return "books/booksList_home";
  			
  			
  		}
  		
  
  	}
  	
  	
 // get all books
 	@GetMapping("/books")
 	public String viewBooks(Model model) {

 		List<Book> listBooks = bookRepo.findAll(); // get list of Books
 		model.addAttribute("listBooks", listBooks);
 		
 	//	List<Author> listAuthors = authorRepo.findAll(); // get list of authors
 	//	model.addAttribute("listAuthors", listAuthors);

 	//	List<Loan> listLoans = loanRepo.findAll(); // get list of loans
 	//	model.addAttribute("listLoans", listLoans);
 		
 		//return "books/booksList_home";
 	
 		
 		return findPaginated(1, "name", "asc", model);

 	}

 	// create book
 	@PostMapping("/books/add")
 	public String createBook(@ModelAttribute("book") Book book) {

 		bookRepo.save(book);

 		return "books/bookDetails";
 		// return "redirect:/books";

 	}

 	// new book form
 	@GetMapping("/books/showNewBookForm")
 	public String showNewBookForm(Model model) {

 		Book book = new Book(); // creating object for the form
 		model.addAttribute("book", book);
 		
 		List<BookDepartment> listBookDepartments = bookDeptRepo.findAll();
 		model.addAttribute("listBookDepartments", listBookDepartments);

 		List<Author> listAuthors = authorRepo.findAll(); // get list of authors
 		model.addAttribute("listAuthors", listAuthors);
 		
 		List<Loan> listLoans = loanRepo.findAll(); // get list of loans
 		model.addAttribute("listLoans", listLoans);

 		return "books/new_bookForm";
 	}

 	// show form for update book
 	@GetMapping("/books/showUpdateBookForm/{id}")
 	public String showUpdateBookForm(@PathVariable(value = "id") Integer id, Model model) {

 		Book book = bookRepo.findById(id).get();
 		model.addAttribute("book", book);
 		
 		List<BookDepartment> listBookDepartments = bookDeptRepo.findAll();
 		model.addAttribute("listBookDepartments", listBookDepartments);

 		List<Author> listAuthors = authorRepo.findAll(); // get list of authors
 		model.addAttribute("listAuthors", listAuthors);
 		
 		List<Loan> listLoans = loanRepo.findAll(); // get list of authors
 		model.addAttribute("listLoans", listLoans);

 		return "books/updateBook";
 	}

 	// update book
 	@PostMapping("/books/updateBook")
 	public String updateBook(@ModelAttribute("book") Book book) {

 		bookRepo.save(book);

 		// return "books/bookDetails";
 		return "redirect:/books";

 	}

 	// view details for Book
 	@GetMapping("/books/viewBookDetails/{id}")
 	public String viewBookDetails(@PathVariable(value = "id") Integer id, Model model) {

 		Book book = bookRepo.findById(id).get();
 		
 		model.addAttribute(book);

 		return "books/bookDetails";

 	}

 	// delete Book
 	@GetMapping("/books/deleteBook/{id}")
 	public String deleteBook(@PathVariable("id") Integer id) {
 		bookRepo.deleteById(id);

 		return "redirect:/books";

 	}
 	
 	//paging and sorting
 			@GetMapping("/page/{pageNo}")
 			public String findPaginated(@PathVariable (value = "pageNo") Integer pageNo, 
 					@RequestParam("sortField") String sortField,
 					@RequestParam("sortDir") String sortDir,
 					Model model) {
 				Integer pageSize = 5;
 				
 				Page<Book> page = bookService.findPaginated(pageNo, pageSize, sortField, sortDir);
 				List<Book> listBooks = page.getContent();
 				
 				model.addAttribute("currentPage", pageNo);
 				model.addAttribute("totalPages", page.getTotalPages());
 				model.addAttribute("totalItems", page.getTotalElements());
 				
 				model.addAttribute("sortField", sortField);
 				model.addAttribute("sortDir", sortDir);
 				model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
 				
 				model.addAttribute("listBooks", listBooks);
 				
 				
 				return "books/booksList_home";
 				
 				
 			}
 }



