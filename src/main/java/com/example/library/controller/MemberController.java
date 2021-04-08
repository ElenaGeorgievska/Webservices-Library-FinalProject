package com.example.library.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.library.FileUploadUtil;
import com.example.library.entity.Book;
import com.example.library.entity.Loan;
import com.example.library.entity.Member;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.MemberRepository;
import com.example.library.services.MemberService;


@Controller
public class MemberController {

	@Autowired
	MemberRepository memberRepo;

	@Autowired
	LoanRepository loanRepo;

	@Autowired
	MemberService memberService;


	 @PostMapping("/members/upload")
	    public String uploadFile(Member member, @RequestParam("image") MultipartFile multipartFile) throws IOException {
		    
		    //RedirectAttributes attributes
		 
	        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	        member.setPhoto(fileName);
	         
	        Member savedMember = memberRepo.save(member);
	 
	        String uploadDir = "./uploadMemberPhoto/" + savedMember.getId();
	 
	        FileUploadUtil.uploadFile(uploadDir, fileName, multipartFile);
	       
	      //  attributes.addFlashAttribute("message", "You successfully uploaded" + fileName + '!');
	        
	        memberRepo.save(member);
	        
	       return "redirect:/members";
	      
	    }
	
		

	// find member page -Home page for member
	@GetMapping("/members/findMember")
	public String findMembersPage(Model model) {
		Member member = new Member();
		model.addAttribute("member", member);
		
		return "members/findMembers";
		
	}

	// search member by name, surname, cardID or embg
	@GetMapping("/members/searchMember")
	public String searchMember(Model model, Member member,String keyword) {
  			
		// return all records possible in database table when we leave empty field for name
		if(member.getName() == (String) null ) {
			member.setName("");
		}
		//findPaginatedHome(1, "name", "asc", model);
		
		// find members
		List<Member> resultsMembers = memberRepo.searchMember(keyword);
		//List<Member> resultsMembers = searchByKeword(member.getName());      //see below searchByKeword(String keyword) method
		
		

		if (resultsMembers.isEmpty()) { // if no members are found after typing a keyword
			// no members found
			List<Member> listMembers = memberRepo.findAll();
			model.addAttribute("listMembers", listMembers);
			
			//return "redirect:/members/findMember";
			return "members/findMembers";

		} else if (resultsMembers.size() == 1) {
			// 1 member found
			member = resultsMembers.iterator().next();
			return "redirect:/members/viewMemberDetails/" + member.getId(); // Here we use display members info method
																			// --> /members/viewMemberDetails/{id} kade
																			// {id} vo nasiot slucaj e member.getId();
			// return "redirect:/members/viewMemberDetails/" +
			// resultsMembers.get(0).getId(); // ovde e primenet metodot
			// /members/viewMemberDetails/{id} kade za id imame +
			// resultsMembers.get(0).getId()

		} else {
			// multiple members found
			//findPaginatedHome(1, "name", "asc", model);
			
			model.addAttribute("listMembers", resultsMembers);
			 
			return "members/memberList_home";
			//return "redirect:/members";
			
		}
	}
	
	List<Member> searchByKeword(String keyword){
		return memberRepo.searchMember(keyword);
	}
	
	
	// get all members
	@GetMapping("/members")
	public String viewMembers(Model model) {

		List<Member> listMembers = memberRepo.findAll(); // get list of Members
		model.addAttribute("listMembers", listMembers);

		// List<Loan> listLoans = loanRepo.findAll(); // get list of loans
		// model.addAttribute("listLoans", listLoans);

		return findPaginatedHome(1, "name", "asc", model);
		 //return "members/memberList_home";

	}

	// create member
	@PostMapping("/members/add")
	public String createMember(@ModelAttribute("member") Member member) {

		memberRepo.save(member);

		return "members/memberDetails";
		// return "redirect:/members";

	}

	// new member form
	@GetMapping("/members/showNewMemberForm")
	public String showNewMemberForm(Model model) {

		Member member = new Member(); // creating object for the form
		model.addAttribute("member", member);

		List<Loan> listLoans = loanRepo.findAll(); // get list of loans
		model.addAttribute("listLoans", listLoans);

		return "members/new_memberForm";
	}

	// show form for update member
	@GetMapping("/members/showUpdateMemberForm/{id}")
	public String showUpdateMemberForm(@PathVariable(value = "id") Integer id, Model model) {

		Member member = memberRepo.findById(id).get();
		model.addAttribute("member", member);

		List<Loan> listLoans = loanRepo.findAll(); // get list of loans
		model.addAttribute("listLoans", listLoans);

		return "members/updateMember";
	}

	// update member
	@PostMapping("/members/updateMember")
	public String updateMember(@ModelAttribute("member") Member member) {

		memberRepo.save(member);

		// return "members/memberDetails";
		return "redirect:/members";

	}

	// view details for Member
	@GetMapping("/members/viewMemberDetails/{id}")
	public String viewMemberDetails(@PathVariable(value = "id") Integer id, Model model) {

		Member member = memberRepo.findById(id).get();

		model.addAttribute("member", member);

		return "members/memberDetails";

	}

	// delete Member
	@GetMapping("/members/deleteMember/{id}")
	public String deleteMember(@PathVariable("id") Integer id) {
		memberRepo.deleteById(id);

		return "redirect:/members";

	}

	// paging and sorting
	@GetMapping("/members/page/{pageNo}")
	public String findPaginatedHome(@PathVariable(value = "pageNo") Integer pageNo,
			@RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir, Model model) {
		Integer pageSize = 5;

		Page<Member> page = memberService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Member> listMembers = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listMembers", listMembers);

		return "members/memberList_home";
		
	}

}
