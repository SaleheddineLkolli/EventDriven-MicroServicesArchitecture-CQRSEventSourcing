package com.saleh.cors_esaxon.commonapi.exceptions;

public class AmountNegativeException  extends RuntimeException{
    public AmountNegativeException(String message){
        super(message);
    }
}
