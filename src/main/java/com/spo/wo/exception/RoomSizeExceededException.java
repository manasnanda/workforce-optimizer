package com.spo.wo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "")
public class RoomSizeExceededException extends Exception
{
    static final long serialVersionUID = -3387516993334229948L;


    public RoomSizeExceededException(String message)
    {
        super(message);
    }

}

