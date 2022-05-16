package com.zzk.springboot.jpa.exception;

public class ResourceNotFoundException extends RuntimeException{
    public static final long serialVersionUID = 1l;

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
