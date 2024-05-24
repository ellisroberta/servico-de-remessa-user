package com.example.servicoderemessauser.validators;

import com.example.servicoderemessauser.annotations.Document;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DocumentValidator implements ConstraintValidator<Document, String> {

    @Override
    public void initialize(Document constraintAnnotation) {
        throw new UnsupportedOperationException("A inicialização não é suportada para esta anotação.");
    }

    @Override
    public boolean isValid(String document, ConstraintValidatorContext context) {
        return isCPF(document) || isCNPJ(document);
    }

    private boolean isCPF(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.chars().allMatch(Character::isDigit)) {
            return false;
        }

        int[] numbers = new int[11];

        // Converte o CPF para um array de inteiros
        for (int i = 0; i < 11; i++) {
            numbers[i] = Character.getNumericValue(cpf.charAt(i));
        }

        // Verifica o primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += numbers[i] * (10 - i);
        }
        int firstVerifierDigit = 11 - (sum % 11);
        if (firstVerifierDigit >= 10) {
            firstVerifierDigit = 0;
        }

        // Verifica o segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += numbers[i] * (11 - i);
        }
        int secondVerifierDigit = 11 - (sum % 11);
        if (secondVerifierDigit >= 10) {
            secondVerifierDigit = 0;
        }

        // Verifica se os dígitos verificadores são iguais aos do CPF
        return firstVerifierDigit == numbers[9] && secondVerifierDigit == numbers[10];
    }

    private boolean isCNPJ(String cnpj) {
        if (cnpj == null || cnpj.length() != 14 || !cnpj.chars().allMatch(Character::isDigit)) {
            return false;
        }

        int[] weights = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int[] numbers = new int[14];

        // Converte o CNPJ para um array de inteiros
        for (int i = 0; i < 14; i++) {
            numbers[i] = Character.getNumericValue(cnpj.charAt(i));
        }

        // Verifica o primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += numbers[i] * weights[i+1];
        }
        int firstVerifierDigit = 11 - (sum % 11);
        if (firstVerifierDigit >= 10) {
            firstVerifierDigit = 0;
        }

        // Verifica o segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 13; i++) {
            sum += numbers[i] * weights[i];
        }
        int secondVerifierDigit = 11 - (sum % 11);
        if (secondVerifierDigit >= 10) {
            secondVerifierDigit = 0;
        }

        // Verifica se os dígitos verificadores são iguais aos do CNPJ
        return firstVerifierDigit == numbers[12] && secondVerifierDigit == numbers[13];
    }
}

