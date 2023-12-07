package com.branch.MessagingSystem.presentation.controller;

import com.branch.MessagingSystem.business.implimentation.MessageServiceImpl;
import com.branch.MessagingSystem.persistence.entities.CannedMessage;
import com.branch.MessagingSystem.persistence.entities.Messages;
import com.branch.MessagingSystem.persistence.enums.Status;
import com.branch.MessagingSystem.persistence.enums.UserType;
import com.branch.MessagingSystem.presentation.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/message")
@CrossOrigin(origins = "http://localhost:4200")
public class MessageController {
    @Autowired
    private MessageServiceImpl messageService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequest messageRequest,@RequestParam Long id){
        MessageResponse messageResponse = messageService.sendMessage(messageRequest,id);
        return ResponseEntity.ok(messageResponse);
    }

    @PostMapping("/respond/{messageId}/{agentId}")
    public ResponseEntity<?> respondToMessage(@PathVariable Long messageId, @PathVariable Long agentId,@RequestBody Response response){
        RespondToMessage respondToMessage = messageService.respondToMessage(messageId,agentId,response);
    return  ResponseEntity.ok(respondToMessage);
    }

    @PostMapping("/respond-with-canned")
    public  ResponseEntity<?> respondToMessageWithCannedMessage(@RequestParam String status,@RequestBody CannedMessageRequest cannedMessageRequest){

           RespondToMessage respondToMessage = messageService.respondToMessageWithCannedMessage(cannedMessageRequest.getMessageId(), cannedMessageRequest.getAgentId(), cannedMessageRequest.getCannedMessageId(), Status.valueOf(status.toUpperCase()));

           return ResponseEntity.ok(respondToMessage);

    }

    @PostMapping("/save-canned-message")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveCannedMessage(@RequestBody CannedMessage cannedMessage){
         cannedMessage = messageService.saveCannedMessage(cannedMessage);
        return ResponseEntity.ok(cannedMessage);
    }

    @PostMapping("/save-is-responded")
    public ResponseEntity<?> saveIsResponded(@RequestBody IsRespondedRequest isRespondedRequest){
        return  ResponseEntity.ok(messageService.updateIsResponded(isRespondedRequest));
    }

    @GetMapping("/get-is-responded")
    public ResponseEntity<?> getIsResponded(@RequestParam Long messageId){
        return  ResponseEntity.ok(messageService.getIsResponded(messageId));

    }
        @GetMapping("/get-canned-messages")
    public ResponseEntity<?> getAllCannedMessages(){
        List<CannedMessage> cannedMessages = messageService.getAllCannedMessages();
        return ResponseEntity.ok(cannedMessages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMessage(@PathVariable Long id){
        return ResponseEntity.ok(messageService.getMessageById(id));
    }

    @GetMapping("/get-messages")
    public ResponseEntity<?> getAllMessages(){
        return ResponseEntity.ok(messageService.getMessages());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessages(@PathVariable Long id){
        return ResponseEntity.ok(messageService.deleteMessageById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchMessages(@RequestParam String keyword){
        List<Messages> messages = messageService.searchMessages(keyword);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages-by-user")
    public ResponseEntity<?> getMessagesByUser(@RequestParam Long id,@RequestParam String userType){
        if (UserType.CUSTOMER == UserType.valueOf(userType.toUpperCase())){

            return ResponseEntity.ok(messageService.findMessageByCustomerId(id));
        } else if (UserType.AGENT == UserType.valueOf(userType.toUpperCase())) {
            return  ResponseEntity.ok(messageService.findMessageByAgentId(id));
        }
        else{
            return ResponseEntity.badRequest().body("invalid user");
        }
    }

}
