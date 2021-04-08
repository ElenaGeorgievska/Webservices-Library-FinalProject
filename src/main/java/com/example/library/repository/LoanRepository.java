package com.example.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.example.library.entity.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {
	

	
	
	//@Query("SELECT l FROM Loan l WHERE l.id LIKE %?1%")
	
	@Query("SELECT l FROM Loan l WHERE CONCAT(l.id, ' ', l.dueDate, ' ') LIKE %?1%" )
	public List<Loan> search(String keyword);

}
