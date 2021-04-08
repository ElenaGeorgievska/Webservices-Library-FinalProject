
package com.example.library.entity;


import java.time.LocalDate;
import java.util.Date;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "loan")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
	
	@Id
	@Column(name= "loanId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column(name= "startDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	
	
	@Column(name= "returnDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date returnDate;
	
	
	@Column(name= "dueDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dueDate;
	
	
	@ManyToOne
	//(cascade = CascadeType.ALL)
	@JoinColumn(name = "bookId")             
	Book book;
	
	@ManyToOne(fetch = FetchType.EAGER)    //, cascade = CascadeType.ALL
	@JoinColumn(name = "memberId")
	Member member;   //borrower
	
	//@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)    // for delete casdade.all
	//private Fines fine;
	

}
