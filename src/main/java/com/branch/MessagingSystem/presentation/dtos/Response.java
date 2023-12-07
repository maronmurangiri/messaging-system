package com.branch.MessagingSystem.presentation.dtos;

import com.branch.MessagingSystem.persistence.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {
    private String status;
    private String response;
}
