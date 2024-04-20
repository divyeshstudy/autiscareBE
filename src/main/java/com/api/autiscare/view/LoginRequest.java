package com.api.autiscare.view;

/**
 * @author divyesh.dwivedi
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class LoginRequest {
    private String username;
    private String password;
}
