package com.branch.MessagingSystem.business.implimentation;

import com.branch.MessagingSystem.business.service.UserService;
import com.branch.MessagingSystem.persistence.entities.Agents;
import com.branch.MessagingSystem.persistence.entities.Customers;
import com.branch.MessagingSystem.persistence.entities.Messages;
import com.branch.MessagingSystem.persistence.repositories.AgentRepository;
import com.branch.MessagingSystem.persistence.repositories.CustomerRepository;
import com.branch.MessagingSystem.presentation.dtos.AgentRequest;
import com.branch.MessagingSystem.presentation.dtos.AgentResponse;
import com.branch.MessagingSystem.presentation.dtos.CustomerRequest;
import com.branch.MessagingSystem.presentation.dtos.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public CustomerResponse registerCustomer(CustomerRequest customerRequest) {
        // Hash the user's password before storing it in the database
        String encodedPassword = bCryptPasswordEncoder.encode(customerRequest.getPassword());
        Customers customers = Customers.builder()
                .username(customerRequest.getUsername())
                .email(customerRequest.getEmail())
                .password(encodedPassword)
                .build();

        Customers customerInDb = customerRepository.findByEmail(customers.getEmail());
        if (customerInDb !=null && customerInDb.equals(customers)){
            throw new IllegalArgumentException("customer by that email exist");
        }
        customers = customerRepository.save(customers);

        return CustomerResponse.builder()
                .id(customers.getCustomerId())
                .username(customers.getUsername())
                .email(customers.getEmail())
                .build();
    }

    @Override
    public AgentResponse registerAgent(AgentRequest agentRequest) {
        // Hash the user's password before storing it in the database
        String encodedPassword = bCryptPasswordEncoder.encode(agentRequest.getPassword());

        Agents agents = Agents.builder()
                .username(agentRequest.getUsername())
                .email(agentRequest.getEmail())
                .password(encodedPassword)
                .build();

        Agents agentInDb = agentRepository.findByEmail(agents.getEmail());
        if (agentInDb !=null && agentInDb.equals(agents)){
            throw new IllegalArgumentException("agent by that email exist");
        }
        agents = agentRepository.save(agents);

        return AgentResponse.builder()
                .id(agents.getAgentId())
                .username(agents.getUsername())
                .email(agents.getEmail())
                .build();
    }

    @Override
    public List<CustomerResponse> getCustomers() {
        List<Customers> customersList = customerRepository.findAll();

        return customersList.stream().map(customers -> CustomerResponse.builder()
                .id(customers.getCustomerId())
                .username(customers.getUsername())
                .email(customers.getEmail())
                .build()).toList();
    }

    @Override
    public List<AgentResponse> getAgents() {
        List<Agents> agentList = agentRepository.findAll();

        return agentList.stream().map(agents -> AgentResponse.builder()
                .id(agents.getAgentId())
                .username(agents.getUsername())
                .email(agents.getEmail())
                .build()).toList();
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {
        return customerRepository.findById(id).map(customers -> CustomerResponse.builder()
                .id(customers.getCustomerId())
                .username(customers.getUsername())
                .email(customers.getEmail())
                .build()).orElse(null);
    }

    @Override
    public AgentResponse getAgentById(Long id) {
        return agentRepository.findById(id).map(agents -> AgentResponse.builder()
                .id(agents.getAgentId())
                .username(agents.getUsername())
                .email(agents.getEmail())
                .build()).orElse(null);
    }

    @Override
    public Boolean deleteCustomerById(Long id) {
        if (customerRepository.existsById(id)){
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<CustomerResponse> searchCustomers(String keyword){
       return customerRepository.findByUsernameContainingOrEmailContaining(keyword,keyword)
               .stream().map(customers -> CustomerResponse.builder()
                       .id(customers.getCustomerId())
                       .username(customers.getUsername())
                       .email(customers.getEmail())
                       . build())
               .toList();


    }

    @Override
    public Customers authenticateCustomer(String email, String password) {
        Customers customer = customerRepository.findByEmail(email);

        if (customer!=null && isPasswordValid(password,customer.getPassword())){
            return customer;
        }
        return null;
    }

    @Override
    public Agents authenticateAgent(String email, String password) {
        Agents agents = agentRepository.findByEmail(email);

        if (agents!=null && isPasswordValid(password,agents.getPassword())){
            return agents;
        }
        return null;
    }

    public boolean isPasswordValid(String inputPassword, String storedPassword) {
        // Verify a password provided by a user against the stored password hash
        return bCryptPasswordEncoder.matches(inputPassword, storedPassword);
    }
}
