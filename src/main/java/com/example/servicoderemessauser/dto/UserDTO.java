package com.example.servicoderemessauser.dto;

import com.example.servicoderemessauser.annotations.Document;
import com.example.servicoderemessauser.enums.UserTypeEnum;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {

    private UUID id;
    private String fullName;
    private String email;
    private String password;
    @Document
    private String document;
    private UserTypeEnum userType;
}
