package com.branch.MessagingSystem.presentation.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgentResponse {
    private Long id;
    private String username;
    private String email;
}
