package com.customerdetailsfetchapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import org.springframework.transaction.annotation.Transactional;

import com.customerdetailsfetchapi.dao.ICustomerRepo;
import com.customerdetailsfetchapi.model.Customer;

@Service
public class CustomerService {
	@Autowired
	private ICustomerRepo customerRepo;
	
	// Get records with pagination and search capabilities
    public Page<Customer> getRecords(String customerId, String accountNumber, String description, Pageable pageable) {
        return customerRepo.findByCustomerIdContainingOrAccountNumberContainingOrDescriptionContaining(
                customerId, accountNumber, description, pageable);
    }
    
    // Update record description with optimistic locking
    @Transactional
    public Customer updateDescription(Long id, String newDescription, Long version) throws Exception {
        Customer record = customerRepo.findById(id).orElseThrow(() -> new Exception("Record not found"));

        // Check if the version matches for optimistic locking
        if (!record.getVersion().equals(version)) {
            throw new Exception("Conflict: The record has been updated by another user.");
        }

        record.setDescription(newDescription);
        return customerRepo.save(record);
    }

}
