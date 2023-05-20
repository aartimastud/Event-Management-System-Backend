package com.ani.ems.exception;

public class DuplicateEventException extends RuntimeException{
    public DuplicateEventException(String msg){
        super(msg);
    }
}
