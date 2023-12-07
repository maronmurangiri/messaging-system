package com.branch.MessagingSystem.presentation.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IsRespondedRequest {
    private Boolean isResponded;
    private Long messageId;
}
