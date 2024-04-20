package com.api.autiscare.responseview;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.Child;
import com.api.autiscare.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class UserChildResponse {

    private User user;
    private List<Child> child;
}
