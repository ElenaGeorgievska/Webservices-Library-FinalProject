package com.example.library.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.time.*;
import java.util.Calendar;
import java.util.List;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.entity.Fines;
import com.example.library.entity.Loan;
import com.example.library.entity.Member;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookDepartmentRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.FineTypeRepository;
import com.example.library.repository.FinesRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.MemberRepository;
import com.example.library.services.AuthorService;
import com.example.library.services.LoanService;

@Controller
public class LoanController {

	@Autowired
	AuthorRepository authorRepo;

	@Autowired
	BookRepository bookRepo;

	@Autowired
	BookDepartmentRepository bookDeptRepo;

	@Autowired
	FinesRepository finesRepo;

	@Autowired
	FineTypeRepository fineTypeRepo;

	@Autowired
	MemberRepository memberRepo;

	@Autowired
	LoanRepository loanRepo;
	
	@Autowired
    LoanService loanService;
	

	
	// get all loans
	/*@GetMapping("/getAllLoans")
	public String viewLoans(Model model) {

		List<Loan> listLoans = loanRepo.findAll(); // get list of Books
		model.addAttribute("listLoans", listLoans);

		return findPaginatedHome(1, "id", "asc", model);
		//return "loans/loansList";

	}*/
	
	   // List of loans, Search
		@GetMapping("/loans")
		public String viewLoanHome(Model model,@Param("keyword") String keyword) {
				
			findPaginatedHome(1, "id", "asc", model);
			
			//List<Loan> listLoans = loanService.searchLoans(keyword);
			List<Loan> listLoans = loanService.searchByBook(keyword);
					
			model.addAttribute("listLoans", listLoans);
			model.addAttribute("keyword", keyword);
			
			return "loans/loansList";
			
		   	
		}

	// create loan
	@PostMapping("/loans/add")
	public String createLoan(@ModelAttribute("loan") Loan loan) {
		
		//we write startDate and dueDate here in crete loan and in new loan form so that we get HH:mm:ss not only YYYY-MM-dd
		Calendar calendar = Calendar.getInstance();
		Date startDate = new Date(calendar.getTimeInMillis());
		loan.setStartDate(startDate);
		
		calendar.add(Calendar.DAY_OF_MONTH, 14);
		Date dueDate = new Date(calendar.getTimeInMillis());
		loan.setDueDate(dueDate);

		loanRepo.save(loan);

		//return "loans/loanDetails";
		 return "redirect:/loans";

	}

	// new loan form
	@GetMapping("/loans/showNewLoanForm")
	public String showNewLoanForm(Model model) {
		
		Loan loan = new Loan(); // creating object for the form
		
		//If we code startDate and dueDate just here and not in create loan we get YYYY-MM-dd without HH:mm:ss 
		Calendar calendar = Calendar.getInstance();
		Date startDate = new Date(calendar.getTimeInMillis());
		loan.setStartDate(startDate);
		
		calendar.add(Calendar.DAY_OF_MONTH, 14);
		Date dueDate = new Date(calendar.getTimeInMillis());
		loan.setDueDate(dueDate);
		
		
		model.addAttribute("loan", loan);
		
		
		List<Book> listBooks = bookRepo.findAll();     // get list of books
		model.addAttribute("listBooks", listBooks);

		List<Member> listMembers = memberRepo.findAll(); // get list of members
		model.addAttribute("listMembers", listMembers);

		List<Fines> listFines = finesRepo.findAll(); // get list of Fines
		model.addAttribute("listFines", listFines);

		return "loans/new_loanForm";
	}

	// show form for update loan
	@GetMapping("/loans/showUpdateLoanForm/{id}")
	public String showUpdateLoanForm(@PathVariable(value = "id") Integer id, Model model) {

		Loan loan = loanRepo.findById(id).get();
		
		model.addAttribute("loan", loan);

		List<Book> listBooks = bookRepo.findAll();     // get list of books
		model.addAttribute("listBooks", listBooks);

		List<Member> listMembers = memberRepo.findAll(); // get list of members
		model.addAttribute("listMembers", listMembers);

		//List<Fines> listFines = finesRepo.findAll(); // get list of Fines
		//model.addAttribute("listFines", listFines);

		return "loans/updateLoanForm";
	}
	
	
	// update loan
	@PostMapping("/loans/updateLoan")
	public String updateLoan(@ModelAttribute("loan") Loan loan) {

		loanRepo.save(loan);

		// return "loans/loanDetails";
		return "redirect:/loans";

	}

	
	//return loan
	@GetMapping("/loans/return/{id}")
	public String returnLoan(@PathVariable("id") Integer id, Model model) { //,Date date
		
		Calendar calendar = Calendar.getInstance();
		Date returnDate = new Date(calendar.getTimeInMillis());
	
		/*Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.HOUR_OF_DAY, hours);
	    calendar.add(Calendar.MINUTE, minutes);
	    calendar.add(Calendar.SECOND, seconds);
	    
	    Date returnDate= calendar.getTime(); */
	
		Loan loan = loanRepo.findById(id).get();
		
		loan.setReturnDate(returnDate);
		
		loanRepo.save(loan);
		
		return "redirect:/loans";
	}
	
	// view details for Loan
	@GetMapping("/loans/viewLoanDetails/{id}")
	public String viewLoanDetails(@PathVariable(value = "id") Integer id, Model model) {

		Loan loan = loanRepo.findById(id).get();

		model.addAttribute(loan);

		return "loans/loanDetails";

	}

	// delete Loan
	@GetMapping("/loans/deleteLoan/{id}")
	public String deleteLoan(@PathVariable("id") Integer id) {
		loanRepo.deleteById(id);

		return "redirect:/loans";

	}
	
	
	//paging and sorting
			@GetMapping("/loans/page/{pageNo}")
			public String findPaginatedHome(@PathVariable (value = "pageNo") Integer pageNo, 
					@RequestParam("sortField") String sortField,
					@RequestParam("sortDir") String sortDir,
					Model model) {
				Integer pageSize = 5;
				
				Page<Loan> page = loanService.findPaginated(pageNo, pageSize, sortField, sortDir);
				List<Loan> listLoans = page.getContent();
				
				model.addAttribute("currentPage", pageNo);
				model.addAttribute("totalPages", page.getTotalPages());
				model.addAttribute("totalItems", page.getTotalElements());
				
				model.addAttribute("sortField", sortField);
				model.addAttribute("sortDir", sortDir);
				model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
				
				model.addAttribute("listLoans", listLoans);
				
				return "loans/loansList";
			}
}
