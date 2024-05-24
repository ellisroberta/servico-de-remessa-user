package com.example.servicoderemessauser.model;

import com.example.servicoderemessauser.annotations.Document;
import com.example.servicoderemessauser.enums.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "document", nullable = false, unique = true)
    @Document
    private String document;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserTypeEnum userType;

    @Column(name = "wallet_id")
    private UUID walletId;
}
