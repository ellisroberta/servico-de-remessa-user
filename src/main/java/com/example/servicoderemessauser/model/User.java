package com.example.servicoderemessauser.model;

import com.example.servicoderemessauser.enums.UserTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 464138816785923116L;
    @Id
    @GeneratedValue
    private UUID id;

    private String fullName;
    private String email;
    private String password;
    private String cpf; //Para PF
    private String cnpj; //Para PJ

    @Enumerated(EnumType.STRING)
    private UserTypeEnum userType;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
//    private Wallet wallet;
}
