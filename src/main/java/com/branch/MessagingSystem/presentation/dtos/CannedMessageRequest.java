package com.branch.MessagingSystem.presentation.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class CannedMessageRequest {
    private Long messageId;
    private Long agentId;
    private Long cannedMessageId;
}
