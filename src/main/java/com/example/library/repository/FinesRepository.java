package com.example.library.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.example.library.entity.Fines;



@Repository
public interface FinesRepository extends JpaRepository<Fines, Integer> {
	
	@Query("SELECT f FROM Fines f WHERE f.member.cardID LIKE %?1%")
	
	
    //@Query("SELECT member.cardID LIKE %?1% FROM Fines fine JOIN fine.member member")
	public List<Fines> searchByMember(String keyword1);
	
	
	


}