package com.customerdetailsfetchapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.customerdetailsfetchapi.model.Customer;
import com.customerdetailsfetchapi.service.CustomerService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/records")
public class RecordController {
	
	 @Autowired
	    private CustomerService recordService;

	    // Retrieve records with pagination and search parameters
	    @GetMapping
	    public Page<Customer> getRecords(
	            @RequestParam(required = false) String customerId,
	            @RequestParam(required = false) String accountNumber,
	            @RequestParam(required = false) String description,
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size) {

	        Pageable pageable = PageRequest.of(page, size);
	        return recordService.getRecords(customerId, accountNumber, description, pageable);
	    }

	    // Update description of a record
	    @PutMapping("/{id}")
	   
	    public ResponseEntity<Customer> updateDescription(
	            @PathVariable Long id,
	            @RequestBody  String newDescription,
	            @RequestHeader("If-Match") Long version) {

	        try {
	            Customer updatedRecord = recordService.updateDescription(id, newDescription, version);
	            return ResponseEntity.ok(updatedRecord);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Handle concurrent update conflict
	        }
	    }

}
