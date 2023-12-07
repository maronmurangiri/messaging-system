package com.branch.MessagingSystem.persistence.repositories;

import com.branch.MessagingSystem.persistence.entities.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Messages,Long> {
    List<Messages> findByMessageContainingOrResponseContaining(String messageKeyword,String responseKeyword);
    @Query("SELECT m FROM Messages m WHERE m.agent.id = :agentId")
    List<Messages> findByAgentId(Long agentId);

    @Query("SELECT m FROM Messages m WHERE m.customer.id = :customerId")
    List<Messages> findByCustomerId(Long customerId);
}
