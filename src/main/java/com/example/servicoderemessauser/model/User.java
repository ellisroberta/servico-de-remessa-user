package com.example.servicoderemessauser.model;

import com.example.servicoderemessauser.enums.UserTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.UUID;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 3709982515321769595L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String fullName;
    private String email;
    private String password;
    private String cpf; //Para PF
    private String cnpj; //Para PJ

    @Enumerated(EnumType.STRING)
    private UserTypeEnum userType;
}
