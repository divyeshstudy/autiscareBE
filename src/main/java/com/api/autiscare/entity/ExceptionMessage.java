package com.api.autiscare.entity;

/**
 * @author divyesh.dwivedi
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionMessage {

    private HttpStatus status;
    private String message;
}
