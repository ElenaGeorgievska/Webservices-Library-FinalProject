package com.example.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.library.entity.BookDepartment;


@Repository
public interface BookDepartmentRepository extends JpaRepository<BookDepartment, Integer> {

}