package com.buyern.inventory.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
/**
"00"= successful,
 "03"= Bad Request,
 "04"= entity already exist,
 "05"= record not found,
 "91"= other errors,
 */
    private String code;
    private String message;
    private Object data;
    private String help;

    public ResponseDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseDTO(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
