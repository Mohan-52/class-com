package com.mohan.class_com.exception;

public class InvalidCredentials extends RuntimeException{
    public InvalidCredentials(String message){
        super(message);
    }
}
