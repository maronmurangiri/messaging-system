package com.branch.MessagingSystem.presentation.dtos;

import com.branch.MessagingSystem.persistence.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private Long id;
    private String username;
    private String email;
}
