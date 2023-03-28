package com.soomee.notesjwt.model.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmptyInputException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private String errorCode;
    private String errorMessage;
    
}
