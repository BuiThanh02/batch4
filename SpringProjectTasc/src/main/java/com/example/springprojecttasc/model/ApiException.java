package com.example.springprojecttasc.model;

import lombok.Data;

import java.util.List;

@Data
public class ApiException extends RuntimeException{
    public ApiException(){
        super();
    }

    private int code;
    private List<?> content;

    public ApiException(int code , String message){
        super(message);
        this.code = code;
    }

    public ApiException(ERROR error){
        super(error.message);
        this.code = error.code;
    }

    public ApiException(ERROR error , String message){
        super(message);
        this.code = error.code;
    }

    public ApiException(ERROR error , String message, List<?> list){
        super(message);
        this.code = error.code;
        this.content = list;
    }
}
