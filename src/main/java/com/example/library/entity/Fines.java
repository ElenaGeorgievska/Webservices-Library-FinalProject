package com.example.library.entity;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fines")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fines {
	
	@Id
	@Column(name= "fineId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "fineType_id")
	private FineType fineType;
	
	@Column(name="fineTotalAmount")
	private double fineTotalAmount;

	@Column(name="paid")
	private boolean paid;
	
	@OneToOne (fetch = FetchType.EAGER)  
	@JoinColumn(name = "loanId")   
	public Loan loan;
	
	@ManyToOne( fetch = FetchType.EAGER)   //cascade = CascadeType.PERSIST
	                                         //cascade = CascadeType.ALL,
	@JoinColumn(name = "memberId")
	public Member member;             
	

}
