package com.api.autiscare.controller;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.User;
import com.api.autiscare.exception.*;
import com.api.autiscare.service.UserMappingService;
import com.api.autiscare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autiscare")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMappingService userMappingService;

    @PostMapping("/user/register")
    public ResponseEntity<User> register(@RequestBody User user) throws DataConstraintVoilationException, UnknownException, DuplicateRecordsException {
        User userResponse = userService.save(user);
        return new ResponseEntity<User>(userResponse, HttpStatus.OK);
    }

/*    @GetMapping("/user")
    public ResponseEntity<List<User>> fetchAllUsers() throws UnknownException {
        List<User> listOfUsers = userService.fetchAllUsers();
        return new ResponseEntity<List<User>>(listOfUsers, HttpStatus.OK);
    }*/

    @GetMapping("/user")
    public ResponseEntity<List<User>> fetchAllUsers() throws UnknownException {
        List<User> listOfUserChildResponse= userService.fetchAllUsers();
        return new ResponseEntity<List<User>>(listOfUserChildResponse, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> fetchUserById(@PathVariable ("id") String userId ) throws NotFoundException, NullValueFound, UnknownException {
        User userResponse = userService.fetchUserById(userId);
        return new ResponseEntity<User>(userResponse, HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUserRecord(@PathVariable ("id") String userId, @RequestBody User user) throws DataConstraintVoilationException, NotFoundException, UnknownException, DuplicateRecordsException {
        User updatedUserRecord = userService.updateUserRecord(userId, user);
        return new ResponseEntity<User>(updatedUserRecord, HttpStatus.OK);

    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable ("id") String userId) throws UnknownException, NullValueFound, NotFoundException {
        userService.deleteUserById(userId);
        return new ResponseEntity<String>("User Id : " + userId +" is deleted successfully.", HttpStatus.OK);
    }

}
