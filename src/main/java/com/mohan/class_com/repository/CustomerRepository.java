package com.mohan.class_com.repository;

import com.mohan.class_com.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByPhoneNumber(Long phoneNumber);
    Optional<Customer> findByUser_Id(Long id);
}
