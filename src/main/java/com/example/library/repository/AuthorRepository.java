package com.example.library.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.library.entity.Author;



@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
	
	
	@Query("SELECT a FROM Author a WHERE a.firstName LIKE %?1%" + "OR a.lastName LIKE %?1%")
	public List<Author> search(String keyword);

	
}