package com.example.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.library.entity.Member;


@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
	
	//get all members by name, surname or card ID
	//@Query("SELECT m FROM Member m WHERE m.name LIKE %:name% or m.surname LIKE %:surname% or m.cardID LIKE %:cardID% or m.embg LIKE %:embg%")
	//@Query("SELECT m FROM Member m WHERE CONCAT(m.name, ' ', m.surname, ' ', m.cardID, ' ', m.embg,' ') LIKE %:keyword%")
	
	@Query("SELECT m FROM Member m WHERE CONCAT(m.name, ' ', m.surname, ' ', m.cardID, ' ', m.embg,' ') LIKE %?1%")
	List<Member> searchMember(@Param("keyword") String keyword);
	
	
	
	

}


