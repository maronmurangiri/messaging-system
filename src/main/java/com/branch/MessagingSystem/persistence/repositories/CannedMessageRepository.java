package com.branch.MessagingSystem.persistence.repositories;

import com.branch.MessagingSystem.persistence.entities.CannedMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CannedMessageRepository extends JpaRepository<CannedMessage,Long> {
}
