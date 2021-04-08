package com.example.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.library.entity.FineType;


@Repository
public interface FineTypeRepository extends JpaRepository<FineType, Integer> {

}