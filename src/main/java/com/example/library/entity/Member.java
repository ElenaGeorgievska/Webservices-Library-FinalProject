package com.example.library.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {

	@Id
	@Column(name = "memberId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column(name = "cardID", unique=true)
	String cardID;

	@Column(name = "name")
	String name;

	@Column(name = "surname")
	String surname;

	@Column(name = "embg")
	Long embg;

	@Column(name = "address")
	String address;

	@Column(name = "email")
	String email;

	@Column(name = "phone")
	String phone;

	@Column(name = "photo")
	String photo;

	@Column(name = "fee")
	Integer membershipFee;

	@Column(name = "joinDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate joinDate;

	@Column(name = "yearOfMembership")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate yearOfMembership;

	//@OneToMany(cascade = CascadeType.ALL)   //fetch = FetchType.LAZY
	//@JoinColumn(name = "loanId")      
	//List<Loan> loans
	
    
	//@OneToMany(cascade = CascadeType.ALL)   
	//private Fines fine;
	//@JoinColumn(name = "fineId")
	//List<Fines> fine;
	
	
	// getter for displaying of uploaded photos
	@Transient
	public String getPhotosImagePath() {
		if (photo == null || id == null)
			return null;

		return "/uploadMemberPhoto/" + id + "/" + photo;
	}

}
