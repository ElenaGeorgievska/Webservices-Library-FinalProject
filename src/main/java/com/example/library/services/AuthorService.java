package com.example.library.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.library.entity.Author;
import com.example.library.repository.AuthorRepository;



@Service
public class AuthorService {
	
	
	@Autowired
	AuthorRepository authorRepo;

	//search authors
	public List<Author> searchAuthors(String keyword) {
		
		if (keyword != null) {
			return authorRepo.search(keyword);
		}
		return authorRepo.findAll();
	}
	
	
	// paging and sorting
		public Page<Author> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
			Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending(): Sort.by(sortField).descending();

			Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
			return this.authorRepo.findAll(pageable);
		}
}
