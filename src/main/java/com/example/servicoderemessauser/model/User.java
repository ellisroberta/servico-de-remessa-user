package com.example.servicoderemessauser.model;

import com.example.servicoderemessauser.enums.UserTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String cpf; //Para PF

    @Column(unique = true)
    private String cnpj; //Para PJ

    @Enumerated(EnumType.STRING)
    private UserTypeEnum userType;
}
