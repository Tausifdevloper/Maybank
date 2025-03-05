package com.customerdetailsfetchapi.dao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.customerdetailsfetchapi.model.Customer;



public interface ICustomerRepo extends JpaRepository<Customer, Long> {

    Page<Customer> findByCustomerIdContainingOrAccountNumberContainingOrDescriptionContaining(
        String customerId, String accountNumber, String description, Pageable pageable);
}