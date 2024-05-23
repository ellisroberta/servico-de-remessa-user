package com.example.servicoderemessauser.annotations;


import com.example.servicoderemessauser.validators.DocumentValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DocumentValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Document {

    String message() default "{Document.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

