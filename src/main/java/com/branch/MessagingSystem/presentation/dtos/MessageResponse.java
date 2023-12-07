package com.branch.MessagingSystem.presentation.dtos;

import com.branch.MessagingSystem.persistence.entities.Agents;
import com.branch.MessagingSystem.persistence.entities.Customers;
import com.branch.MessagingSystem.persistence.enums.Priority;
import com.branch.MessagingSystem.persistence.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {
    private Long id;
    private String message;
    private Timestamp createdTime;
    private Priority priority;
    private Status status;
    private String response;
    //private Agents agent;
   // private Customers customers;


}
