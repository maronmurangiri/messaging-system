package com.branch.MessagingSystem.persistence.entities;

import com.branch.MessagingSystem.persistence.enums.Priority;
import com.branch.MessagingSystem.persistence.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Timestamp createdTime;
    private Priority priority;
    private Status status;
    private String response;
    private Boolean isResponded;

    @ManyToOne(targetEntity = Agents.class)
    @JoinColumn(name = "agentId")
    private Agents agent;

    @ManyToOne(targetEntity = Customers.class)
    @JoinColumn(name = "customerId")
    private Customers customer;

}
