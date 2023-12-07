package com.branch.MessagingSystem.business.service;

import com.branch.MessagingSystem.persistence.entities.CannedMessage;
import com.branch.MessagingSystem.persistence.entities.Messages;
import com.branch.MessagingSystem.persistence.enums.Status;
import com.branch.MessagingSystem.presentation.dtos.MessageRequest;
import com.branch.MessagingSystem.presentation.dtos.MessageResponse;
import com.branch.MessagingSystem.presentation.dtos.RespondToMessage;
import com.branch.MessagingSystem.presentation.dtos.Response;

import java.util.List;

public interface MessageService {
    MessageResponse sendMessage(MessageRequest messageRequest,Long id);
    List<MessageResponse> getMessages();
    MessageResponse getMessageById(Long id);
    Boolean deleteMessageById(Long id);
    List<Messages> searchMessages(String keyword);
    RespondToMessage respondToMessage(Long messageId, Long agentId, Response response);

    List<CannedMessage> getAllCannedMessages();
    RespondToMessage respondToMessageWithCannedMessage(Long messageId, Long agentId, Long cannedMessageId, Status status);

    CannedMessage saveCannedMessage(CannedMessage cannedMessage);


}
