package com.branch.MessagingSystem.persistence.repositories;

import com.branch.MessagingSystem.persistence.entities.Customers;
import com.branch.MessagingSystem.presentation.dtos.CustomerResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customers,Long> {

    Customers findByEmail(String email);
    List<Customers> findByUsernameContainingOrEmailContaining(String usernameKeyword, String emailKeyword);
}
