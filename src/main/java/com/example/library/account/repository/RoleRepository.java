package com.example.library.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.library.account.entity.Role;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByName(String name);

}
