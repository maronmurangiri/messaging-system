package com.branch.MessagingSystem.business.implimentation;

import com.branch.MessagingSystem.business.service.MessageService;
import com.branch.MessagingSystem.persistence.entities.Agents;
import com.branch.MessagingSystem.persistence.entities.CannedMessage;
import com.branch.MessagingSystem.persistence.entities.Customers;
import com.branch.MessagingSystem.persistence.entities.Messages;
import com.branch.MessagingSystem.persistence.enums.Priority;
import com.branch.MessagingSystem.persistence.enums.Status;
import com.branch.MessagingSystem.persistence.repositories.AgentRepository;
import com.branch.MessagingSystem.persistence.repositories.CannedMessageRepository;
import com.branch.MessagingSystem.persistence.repositories.CustomerRepository;
import com.branch.MessagingSystem.persistence.repositories.MessageRepository;
import com.branch.MessagingSystem.presentation.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
public class MessageServiceImpl implements MessageService {
    Timestamp currentTimestamp = Timestamp.from(Instant.now());
    String pattern = "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    Timestamp formattedTimestamp = Timestamp.valueOf(currentTimestamp.toLocalDateTime().format(dateTimeFormatter));

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private CannedMessageRepository cannedMessageRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    @Override
    public MessageResponse sendMessage(MessageRequest messageRequest,Long id) {
        Customers customers = null;

        if (customerRepository.findById(id).isPresent()){
         customers = customerRepository.findById(id).get();
        }


        Messages messages = Messages.builder()
                .message(messageRequest.getMessage())
                .createdTime(formattedTimestamp)
                .priority(calculatePriority(messageRequest))
                .status(Status.OPEN)
                .customer(customers)
                .build();

        // Assign the message to an agent based on workload
        Agents assignedAgents = assignMessageToAgent();

        //set assigned agent to message
        messages.setAgent(assignedAgents);

        // Increment the workload of the assigned agent
        assignedAgents.setWorkload(assignedAgents.getWorkload()+1);


         Messages createdMessage = messageRepository.save(messages);
         agentRepository.save(assignedAgents);

        return MessageResponse.builder()
                .id(createdMessage.getId())
                .message(createdMessage.getMessage())
                .status(createdMessage.getStatus())
                .priority(createdMessage.getPriority())
                .createdTime(formattedTimestamp)
                .response(Optional.ofNullable(createdMessage.getResponse()).orElse(""))
                .build();

    }

    private Agents assignMessageToAgent() {
        //retrieve all agents sorted by workload(Asc)
        List<Agents> availableAgents = agentRepository.findAllByOrderByWorkloadAsc();

        return availableAgents.get(0);
    }


    private Priority calculatePriority(MessageRequest messageRequest) {
        String message = messageRequest.getMessage().toLowerCase();
       // String[] keywordArray = {"loan","approval","approve","disbursed","payment","batch","process,rejected","cleared"};
        //String[] high = {"unable","Account","blocked","locked"};
        if(containsAny(message,"loan","approval","approve","disbursed","payment","batch","process,rejected","cleared")){
            return Priority.URGENT;
        } else if (containsAny(message,"unable","Account","blocked","locked")) {
            return Priority.HIGH;
        }else {
            return Priority.MEDIUM;
        }
    }

    private boolean containsAny(String message, String... keywordArray) {
        for (String keyword : keywordArray){
            if (message.contains(keyword)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<MessageResponse> getMessages() {
         ;
        return messageRepository.findAll().stream().map(messages -> MessageResponse.builder()
                .id(messages.getId())
                .message(messages.getMessage())
                .createdTime(messages.getCreatedTime())
                .priority(messages.getPriority())
                .status(messages.getStatus())
                .build()).toList();

    }

    @Override
    public MessageResponse getMessageById(Long id) {
        Optional<Messages> retrievedMessage = messageRepository.findById(id);
        return retrievedMessage.map(messages -> MessageResponse.builder()
                .id(messages.getId())
                .message(messages.getMessage())
                .createdTime(messages.getCreatedTime())
                .priority(messages.getPriority())
                .status(messages.getStatus())
                .build()).orElse(null);
    }

    @Override
    public Boolean deleteMessageById(Long id) {
        if (messageRepository.existsById(id)){
        messageRepository.deleteById(id);
        return true;
        }
        else {
            return false;
        }
    }

    @Override
    public List<Messages> searchMessages(String messages){
        return messageRepository.findByMessageContainingOrResponseContaining(messages,messages);
    }

    @Override
    public RespondToMessage respondToMessage(Long messageId, Long agentId, Response response){
        //retrieve the message and the agent
        Optional<Messages> optionalMessage = messageRepository.findById(messageId);
        Optional<Agents> optionalAgent = agentRepository.findById(agentId);

        if (optionalMessage.isPresent() && optionalAgent.isPresent()) {
            Messages messages = optionalMessage.get();
            Agents agents = optionalAgent.get();

            messages.setResponse(response.getResponse());
            messages.setStatus(Status.valueOf(response.getStatus().toUpperCase()));

            Messages updatedMessage = messageRepository.save(messages);
            return RespondToMessage.builder()
                    .id(updatedMessage.getId())
                    .message(updatedMessage.getMessage())
                    .response(updatedMessage.getResponse())
                    .status(updatedMessage.getStatus())
                    .build();

    }else {
        return null;
        }
    }
    public IsRespondedRequest updateIsResponded( IsRespondedRequest isRespondedRequest){
        Optional<Messages> optionalMessages = messageRepository.findById(isRespondedRequest.getMessageId());
        Messages messages = null;
        if (optionalMessages.isPresent()){
             messages = optionalMessages.get();
            System.out.println(isRespondedRequest);
            messages.setIsResponded(isRespondedRequest.getIsResponded());
              Messages updatedMessage =messageRepository.save(messages);
            System.out.println(updatedMessage);
            return IsRespondedRequest.builder()
                    .messageId(updatedMessage.getId())
                    .isResponded(updatedMessage.getIsResponded())
                    .build();
        }
        return null;
    }
    public IsRespondedRequest getIsResponded(Long messageId){
        Optional<Messages> optionalMessages = messageRepository.findById(messageId);
        if (optionalMessages.isPresent()){
            Messages messages = optionalMessages.get();
            return IsRespondedRequest.builder()
                    .messageId(messages.getId())
                    .isResponded(messages.getIsResponded())
                    .build();

        }
        return null;
    }

    @Override
    public List<CannedMessage> getAllCannedMessages(){
        return cannedMessageRepository.findAll();
    }

    @Override
    public RespondToMessage respondToMessageWithCannedMessage(Long messageId, Long agentId, Long cannedMessageId,Status status){
        Optional<Messages> optionalMessages = messageRepository.findById(messageId);
        Optional<Agents> optionalAgent = agentRepository.findById(agentId);
        Optional<CannedMessage> optionalCannedMessage = cannedMessageRepository.findById(cannedMessageId);

        if (optionalMessages.isPresent() && optionalAgent.isPresent() && optionalCannedMessage.isPresent()){
            Messages messages = optionalMessages.get();
            Agents agents = optionalAgent.get();
            CannedMessage cannedMessage = optionalCannedMessage.get();

            messages.setResponse(cannedMessage.getContent());

            messages.setStatus(status);

            Messages updatedMessage = messageRepository.save(messages);

            return RespondToMessage.builder()
                    .id(updatedMessage.getId())
                    .message(updatedMessage.getMessage())
                    .response(updatedMessage.getResponse())
                    .status(updatedMessage.getStatus())
                    .build();
        }else {
            return null;
        }
    }

    @Override
    public CannedMessage saveCannedMessage(CannedMessage cannedMessage){
       return cannedMessageRepository.save(cannedMessage);
    }

    public List<MessageResponse> findMessageByAgentId(Long agentId){
      List<Messages> messageFound =  messageRepository.findByAgentId(agentId);
        return messageFound.stream().map(messages -> MessageResponse.builder()
                .id(messages.getId())
                .message(messages.getMessage())
                .status(messages.getStatus())
                .priority(messages.getPriority())
                .createdTime(messages.getCreatedTime())
                .build()).toList()
                ;
    }

    public List<MessageResponse> findMessageByCustomerId(Long customerId) {
        List<Messages> messageFound = messageRepository.findByCustomerId(customerId);

        return messageFound.stream().map(messages -> {
            // Use Optional to handle null response
            String response = Optional.ofNullable(messages.getResponse()).orElse("");

            return MessageResponse.builder()
                    .id(messages.getId())
                    .message(messages.getMessage())
                    .status(messages.getStatus())
                    .priority(messages.getPriority())
                    .createdTime(messages.getCreatedTime())
                    .response(response)
                    .build();
        }).toList();
    }
}
