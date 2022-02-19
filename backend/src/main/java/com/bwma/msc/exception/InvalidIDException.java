package com.bwma.msc.exception;

public class InvalidIDException extends RuntimeException{

    public InvalidIDException(){
        super();
    }

    public  InvalidIDException(String msg){
        super(msg);
    }
}
