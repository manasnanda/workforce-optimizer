package com.spo.wo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "")
public class InvalidRoomSizeException extends Exception
{
    static final long serialVersionUID = -3387516993334229948L;


    public InvalidRoomSizeException(String message)
    {
        super(message);
    }

}

