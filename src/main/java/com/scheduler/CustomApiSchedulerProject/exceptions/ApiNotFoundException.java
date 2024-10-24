package com.scheduler.CustomApiSchedulerProject.exceptions;

public class ApiNotFoundException extends RuntimeException{
    public ApiNotFoundException(String msg){
        super(msg);
    }
}
