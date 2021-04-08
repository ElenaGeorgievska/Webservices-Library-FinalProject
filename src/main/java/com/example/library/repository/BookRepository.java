package com.example.library.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.example.library.entity.Book;



@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	//get all book by name		

    
	//@Query("SELECT b FROM Book b WHERE CONCAT(b.name, ' ', b.author.firstName, ' ', b.author.lastName, ' ') LIKE %?1%")
	 //List<Book> searchBook1 (@Param("keyword") String keyword);	
	
	//@Query("SELECT b FROM Book b WHERE b.name LIKE %?1%")
	

	@Query("SELECT b FROM Book b WHERE b.name LIKE %:name%")
    List<Book> searchBook (@Param("name") String name);
	
		
}