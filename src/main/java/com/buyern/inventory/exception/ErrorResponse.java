package com.buyern.inventory.exception;

import com.buyern.inventory.dtos.ResponseDTO;

public class ErrorResponse extends ResponseDTO {

    public ErrorResponse(String code, String message, Object data) {
        super(code, message, data);
    }

    public ErrorResponse(String code, String message) {
        super(code, message);
    }
}
