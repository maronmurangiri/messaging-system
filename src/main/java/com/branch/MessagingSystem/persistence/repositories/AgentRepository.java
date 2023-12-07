package com.branch.MessagingSystem.persistence.repositories;

import com.branch.MessagingSystem.persistence.entities.Agents;
import org.aspectj.weaver.loadtime.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentRepository extends JpaRepository<Agents,Long> {


    Agents findByEmail(String email);
    List<Agents> findAllByOrderByWorkloadAsc();
}
