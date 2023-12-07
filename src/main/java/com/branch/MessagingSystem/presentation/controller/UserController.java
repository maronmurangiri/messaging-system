package com.branch.MessagingSystem.presentation.controller;

import com.branch.MessagingSystem.business.service.UserService;
import com.branch.MessagingSystem.persistence.entities.Agents;
import com.branch.MessagingSystem.persistence.entities.Customers;
import com.branch.MessagingSystem.persistence.enums.UserType;
import com.branch.MessagingSystem.presentation.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create-account")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto){

        if (UserType.CUSTOMER == UserType.valueOf(userDto.getUserType().toUpperCase())){
            CustomerRequest customerRequest = CustomerRequest.builder()
                    .username(userDto.getUsername())
                    .email(userDto.getEmail())
                    .userType(UserType.valueOf(userDto.getUserType().toUpperCase()))
                    .password(userDto.getPassword())
                    .build();

            return  ResponseEntity.ok(userService.registerCustomer(customerRequest));
        } else if (UserType.AGENT==UserType.valueOf(userDto.getUserType().toUpperCase())) {
            AgentRequest agentRequest = AgentRequest.builder()
                    .username(userDto.getUsername())
                    .email(userDto.getEmail())
                    .userType(UserType.valueOf(userDto.getUserType().toUpperCase()))
                    .password(userDto.getPassword())
                    .build();

           return ResponseEntity.ok(userService.registerAgent(agentRequest));// user = userService.registerAgent(userDto);
        }else {
            return ResponseEntity.badRequest().body("Invalid user type");
        }
        //return ResponseEntity.ok("User registered with email: "+user.getEmail());
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody UserSignInRequest userSignInRequest) {
        String email = userSignInRequest.getEmail();
        String password = userSignInRequest.getPassword();
        UserType userType = UserType.valueOf(userSignInRequest.getUserType().toUpperCase());
        UserSignInResponse response;
        if (userType==UserType.CUSTOMER){
            Customers customers = userService.authenticateCustomer(email,password);
            if (customers==null){
                return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }
            return ResponseEntity.ok(response = UserSignInResponse.builder()
                    .id(customers.getCustomerId())
                    .email(customers.getEmail())
                    .userType(userSignInRequest.getUserType())
                    .build());
            //return ResponseEntity.ok("Login Success");
        }
        else if (userType == UserType.AGENT) {
            Agents agents = userService.authenticateAgent(email,password);
            if (agents==null){
                return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }

            return  ResponseEntity.ok(response = UserSignInResponse.builder()
                    .id(agents.getAgentId())
                    .email(agents.getEmail())
                    .userType(userSignInRequest.getUserType())
                    .build());

            //return  ResponseEntity.ok("Login Success");
        }
        return null;
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id){
        return  ResponseEntity.ok(userService.getCustomerById(id));
    }

    @GetMapping("/agent/{id}")
    public ResponseEntity<?> getAgentById(@PathVariable Long id){
        return  ResponseEntity.ok(userService.getAgentById(id));
    }

    @GetMapping("/customers")
    public ResponseEntity<?> getCustomers(){
        return ResponseEntity.ok(userService.getCustomers());
    }

    @GetMapping("/agents")
    public ResponseEntity<?> getAgents(){
        return ResponseEntity.ok(userService.getAgents());
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteCustomerById(id));
    }

    @GetMapping("/customers/search")
    public  ResponseEntity<?> searchCustomers(@RequestParam String keyword){
        List<CustomerResponse> customers = userService.searchCustomers(keyword);
        return ResponseEntity.ok(customers);
    }

}
