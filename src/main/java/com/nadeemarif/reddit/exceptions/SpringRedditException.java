package com.nadeemarif.reddit.exceptions;

public class SpringRedditException extends RuntimeException{
    public SpringRedditException(String exMessage){
        super(exMessage);
    }
}
