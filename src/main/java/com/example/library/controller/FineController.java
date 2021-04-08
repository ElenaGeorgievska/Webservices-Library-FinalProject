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


import com.example.library.entity.FineType;
import com.example.library.entity.Fines;
import com.example.library.entity.Loan;
import com.example.library.entity.Member;
import com.example.library.repository.FineTypeRepository;
import com.example.library.repository.FinesRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.MemberRepository;
import com.example.library.services.FineService;

@Controller
public class FineController {
	
	@Autowired
	FinesRepository finesRepo;
	
	@Autowired
    FineTypeRepository fineTypeRepo;
	
    @Autowired
    LoanRepository loanRepo;
    
    @Autowired
    FineService fineService;
    
    @Autowired
    MemberRepository memberRepo;

    
 // get all fines
/* 	@GetMapping("/fines")
 	public String viewFines(Model model) {

 		List<Fines> listFines = finesRepo.findAll(); // get list of Fines
 		model.addAttribute("listFines", listFines);
 		
 		return findPaginatedHome(1, "id", "asc", model);
 		//return "fines/fineList_home";

 	}*/
    
    // List of loans, Search
  		@GetMapping("/fines")
  		public String viewLoanHome(Model model,@Param("keyword1") String keyword1) {
  					
  			findPaginatedHome(1, "id", "asc", model);
  			
  			List<Fines> listFines = fineService.searchFines(keyword1);
  			//List<Fines> listFines = fineService.searchByMemberCardID(keyword1);
  			
  					
  					
  			model.addAttribute("listFines", listFines);
  			model.addAttribute("keyword1", keyword1);
  			
  			return "fines/fineList_home";	   	
  		}

 	// create fine
 	@PostMapping("/fines/add") 
 	public String createFine(@ModelAttribute("fine") Fines fine) {

 		finesRepo.save(fine);

 		return "fines/fineDetails";
 		// return "redirect:/fines";

 	}

 	// new fine form
 	@GetMapping("/fines/showNewFineForm")
 	public String showNewFineForm(Model model) {

 		Fines fine = new Fines(); // creating object for the form
 		model.addAttribute("fine", fine);
 		
 		List<FineType> listFineType = fineTypeRepo.findAll();   //get list of fine types
 		model.addAttribute("listFineType", listFineType);
 		
 		List<Loan> listLoans = loanRepo.findAll(); // get list of loans
 		model.addAttribute("listLoans", listLoans);
 		
 		List<Member> listMembers = memberRepo.findAll(); // get list of members
 		model.addAttribute("listMembers", listMembers);

 		return "fines/new_fineForm";
 	}

 	// show form for update fine
 	@GetMapping("/fines/showUpdateFineForm/{id}")
 	public String showUpdateFineForm(@PathVariable(value = "id") Integer id, Model model) {

 		Fines fine = finesRepo.findById(id).get();
 		model.addAttribute("fine", fine);
 		 
 		List<FineType> listFineType = fineTypeRepo.findAll();  //get list of fine types
 		model.addAttribute("listFineType", listFineType);
 		
 		List<Loan> listLoans = loanRepo.findAll(); // get list of loans
 		model.addAttribute("listLoans", listLoans);
 		
 		List<Member> listMembers = memberRepo.findAll(); // get list of members
 		model.addAttribute("listMembers", listMembers);

 		return "fines/updateFine";
 	}

 	// update Fine
 	@PostMapping("/fines/updateFine")
 	public String updateFine(@ModelAttribute("fine") Fines fine) {

 		finesRepo.save(fine);

 		// return "fines/fineDetails";
 		return "redirect:/fines";

 	}

 	// view details for Fine
 	@GetMapping("/fines/viewFineDetails/{id}")
 	public String viewFineDetails(@PathVariable(value = "id") Integer id, Model model) {

 		Fines fine = finesRepo.findById(id).get();
 		
 		model.addAttribute("fine",fine);

 		return "fines/fineDetails";

 	}

 	// delete Fine
 	@GetMapping("/fines/deleteFine/{id}")
 	public String deleteFine(@PathVariable("id") Integer id) {
 		
 		finesRepo.deleteById(id);

 		return "redirect:/fines";

 	}
 	
 	
 	//paging and sorting
 			@GetMapping("/fines/page/{pageNo}")
 			public String findPaginatedHome(@PathVariable (value = "pageNo") Integer pageNo, 
 					@RequestParam("sortField") String sortField,
 					@RequestParam("sortDir") String sortDir,
 					Model model) {
 				Integer pageSize = 5;
 				
 				Page<Fines> page = fineService.findPaginated(pageNo, pageSize, sortField, sortDir);
 				List<Fines> listFines = page.getContent();
 				
 				model.addAttribute("currentPage", pageNo);
 				model.addAttribute("totalPages", page.getTotalPages());
 				model.addAttribute("totalItems", page.getTotalElements());
 				
 				model.addAttribute("sortField", sortField);
 				model.addAttribute("sortDir", sortDir);
 				model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
 				
 				model.addAttribute("listFines", listFines);
 				
 				
 				return "fines/fineList_home";
 			}

}
