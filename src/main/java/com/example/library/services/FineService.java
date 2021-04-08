package com.example.library.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.library.entity.Fines;
import com.example.library.repository.FinesRepository;

@Service
public class FineService {

	@Autowired
	FinesRepository finesRepo;

	// search fine by Member card ID, Loan id, and Fine id
	public List<Fines> searchByMemberCardID(String keyword1) {

		if (keyword1 != null) {

			List<Fines> fines = finesRepo.findAll();
			List<Fines> filteredFines = new ArrayList<>();
			for (Fines fine : fines) {
				if ( fine.getMember().getCardID().toString().equals(keyword1)
					//	||	fine.getLoan().getId().toString().equals(keyword)
					 
						
					//	|| fine.getId().toString().equals(keyword)
						) {

					filteredFines.add(fine);
				}
			}

			return filteredFines;
		} else {

			return finesRepo.findAll();
		}
	}

	// search loans

	public List<Fines> searchFines(String keyword1) {

		if (keyword1 != null) {
			return finesRepo.searchByMember(keyword1);
		}

		return finesRepo.findAll();
	}

	// paging and sorting
	public Page<Fines> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.finesRepo.findAll(pageable);
	}
}
