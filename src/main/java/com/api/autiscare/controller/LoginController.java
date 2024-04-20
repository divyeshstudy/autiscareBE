package com.api.autiscare.controller;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.User;
import com.api.autiscare.exception.NotFoundException;
import com.api.autiscare.service.UserService;
import com.api.autiscare.view.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autiscare")
public class LoginController {

    @Autowired
    LoginRequest loginRequest;

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) throws NotFoundException {
        User user = userService.userLogin(loginRequest);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
