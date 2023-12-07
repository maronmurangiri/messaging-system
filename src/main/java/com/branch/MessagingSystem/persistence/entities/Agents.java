package com.branch.MessagingSystem.persistence.entities;

import com.branch.MessagingSystem.persistence.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Agents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agentId;
    private String username;
    private String email;
    private String password;
    private int workload;

    @ManyToMany()
    @JoinTable(
            joinColumns = @JoinColumn(name = "agentId"),
            inverseJoinColumns = @JoinColumn(name = "id"))
        private List<CannedMessage> cannedMessages;
}
