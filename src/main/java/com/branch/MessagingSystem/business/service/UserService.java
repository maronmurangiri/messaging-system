package com.branch.MessagingSystem.business.service;


import com.branch.MessagingSystem.persistence.entities.Agents;
import com.branch.MessagingSystem.persistence.entities.Customers;
import com.branch.MessagingSystem.persistence.entities.Messages;
import com.branch.MessagingSystem.presentation.dtos.AgentRequest;
import com.branch.MessagingSystem.presentation.dtos.AgentResponse;
import com.branch.MessagingSystem.presentation.dtos.CustomerRequest;
import com.branch.MessagingSystem.presentation.dtos.CustomerResponse;

import java.util.List;

public interface UserService {

    CustomerResponse registerCustomer(CustomerRequest customerRequest);
    AgentResponse registerAgent(AgentRequest agentRequest);
    List<CustomerResponse> getCustomers();
    List<AgentResponse> getAgents();

    CustomerResponse getCustomerById(Long id);
    AgentResponse getAgentById(Long id);
    Boolean deleteCustomerById(Long id);

    List<CustomerResponse> searchCustomers(String keyword);
    Customers authenticateCustomer(String email, String password);
    Agents authenticateAgent(String email, String password);

}
