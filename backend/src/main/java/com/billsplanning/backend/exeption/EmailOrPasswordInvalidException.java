package com.billsplanning.backend.exeption;


public class EmailOrPasswordInvalidException extends RuntimeException {
    public EmailOrPasswordInvalidException(String message) {
        super(message);
    }
}