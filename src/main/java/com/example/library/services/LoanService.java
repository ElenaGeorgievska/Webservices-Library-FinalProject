package com.example.library.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.library.entity.Loan;
import com.example.library.repository.LoanRepository;

@Service
public class LoanService {

	@Autowired
	LoanRepository loanRepo;

	// search loan by book, member, dueDate, and id
	public List<Loan> searchByBook(String keyword) {

		if (keyword != null) {

			List<Loan> loans = loanRepo.findAll();
			List<Loan> filteredLoans = new ArrayList<>();
			for (Loan loan : loans) {
				if (loan.getBook().getName().toString().equals(keyword)
						|| loan.getDueDate().toString().contains(keyword) || loan.getId().toString().equals(keyword)
						|| loan.getMember().getCardID().toString().equals(keyword)) {

					filteredLoans.add(loan);
				}
			}

			return filteredLoans;
		} else {

			return loanRepo.findAll();
		}
	}

	// search loans
	public List<Loan> searchLoans(String keyword) {

		if (keyword != null) {
			return loanRepo.search(keyword);
		}
		return loanRepo.findAll();
	}

	// paging and sorting
	public Page<Loan> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.loanRepo.findAll(pageable);
	}
}
