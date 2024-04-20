package com.api.autiscare.service;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.User;
import com.api.autiscare.exception.*;
import com.api.autiscare.view.LoginRequest;

import java.util.List;

public interface UserService {
    public User save(User user) throws DataConstraintVoilationException, UnknownException, DuplicateRecordsException;;

    public List<User> fetchAllUsers() throws UnknownException;

    public User userLogin(LoginRequest loginRequest) throws NotFoundException;

    public User fetchUserById(String userId) throws NotFoundException, UnknownException, NullValueFound;

    public User updateUserRecord(String userId, User user) throws NotFoundException, UnknownException, DataConstraintVoilationException, DuplicateRecordsException;

    public void deleteUserById(String userId) throws NotFoundException, NullValueFound, UnknownException;

}
