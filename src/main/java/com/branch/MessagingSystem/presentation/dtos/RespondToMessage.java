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
public class RespondToMessage {
    private Long id;
    private String message;
    private String response;
    private Status status;
}
