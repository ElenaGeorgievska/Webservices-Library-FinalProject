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
import javax.persistence.OneToMany;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "author")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
	
	@Id
	@Column(name= "authorId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column(name= "firstName")
	String firstName;                //name
	
	@Column(name= "lastName")
	String lastName;
	
	@Column(name= "description")
	String description;
	
	//@ManyToMany(fetch = FetchType.EAGER)
	//List<Book> Books;
	
	//@OneToMany (cascade = CascadeType.ALL)   //adding books in authors table
	//@JoinColumn(name = "bookId")
	//List<Book> Books;

}
