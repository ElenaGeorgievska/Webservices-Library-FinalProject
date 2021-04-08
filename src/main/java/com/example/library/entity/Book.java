package com.example.library.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
	
	@Id
	@Column(name= "bookId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column(name= "isbn")
	String isbn;
	
	@Column(name= "name")
	String name;
	
	@Column(name= "edition")
	String edition;
	
	@Column(name= "price")
	Double price;
	
	@ManyToOne
	@JoinColumn(name = "department_id")
	private BookDepartment department;
	
	@Column(name= "numberOfCopies")
	Integer numberOfCopies;
	
	@Column(name="isAvailable")
	Boolean isAvailable;
	
	@Column(name = "photo")
	String photo;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "books_author", joinColumns = @JoinColumn(name = "bookId"),
	inverseJoinColumns = @JoinColumn(name = "authorId"))
	List<Author> authors;
	
	//@OneToMany (cascade = CascadeType.ALL)   
	//@JoinColumn(name = "loanId")
	//List<Loan> loans;                       
	
	
	// getter for displaying of uploaded photos
		@Transient
		public String getPhotosImagePath() {
			if (photo == null || id == null)
				return null;

			return "/uploadBookPhoto/" + id + "/" + photo;
		}

}
