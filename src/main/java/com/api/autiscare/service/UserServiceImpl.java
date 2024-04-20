package com.api.autiscare.service;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.User;
import com.api.autiscare.exception.*;
import com.api.autiscare.repository.UserRepository;
import com.api.autiscare.view.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserMappingService userMappingService;

    @Override
    public User save(User user) throws DataConstraintVoilationException, UnknownException, DuplicateRecordsException {
        try {
            if(user.getUserType().equals("A") ||
                    user.getUserType().equals("C")||
                    user.getUserType().equals("T")||
                    user.getUserType().equals("TC")){
                userDuplicateRecordCheck(user);
                User persistedUser = userRepository.save(user);
            /*UserMapping userMapping = new UserMapping().builder()
                    .userId(persistedUser.getUserId())
                    .userType(persistedUser.getUserType())
                    .build();

            userMappingServiceImpl.save(userMapping);*/
                logger.info("User record with ID = {} is saved successfully.", persistedUser.getUserId());
                return persistedUser;
            }
            else {
                throw new Exception("User type mismatch.");
            }
        } catch (DataIntegrityViolationException e) {
            logger.error("Error while saving user record : " + e.getRootCause());
            throw new DataConstraintVoilationException(e.getRootCause());
        } catch (DuplicateRecordsException e) {
            logger.error("Error while saving user record : " + e.getMessage());
            throw new DuplicateRecordsException(e.getMessage());
        }catch (Exception e) {
            logger.error("Error while saving user record : " + e.getMessage());
            throw new UnknownException(e.getMessage());
        }
    }

    @Override
    public List<User> fetchAllUsers() throws UnknownException {
        try {
            List<User> fetchedUsers = userRepository.findAll();
            logger.info("User records are fetched successfully.");
            return fetchedUsers;
        } catch (Exception e) {
            logger.error("Error while fetching all the user records : " + e);
            throw new UnknownException("Error while fetching all the user records" + e.getCause());
        }
    }

    @Override
    public User userLogin(LoginRequest loginRequest) throws NotFoundException {
        try{
            User user = userRepository.findByUsername(loginRequest.getUsername());
            if (Objects.isNull(user)){
                throw new NotFoundException("No user found with the username");
            }
            if(loginRequest.getUsername().equals(user.getUsername())){
                if (loginRequest.getPassword().equals(user.getPassword())){
                    return user;
                }
                else{
                    throw new NotFoundException("Password is incorrect");
                }
            }else{
                throw new NotFoundException("Username not found");
            }

        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }



    @Override
    public User fetchUserById(String userId) throws NotFoundException, NullValueFound, UnknownException {
        try {
            if (Objects.isNull(userId)) {
                logger.error("User ID is null");
                throw new NullValueFound("User ID is null.");
            }
            Optional<User> user = userRepository.findById(userId);
            if (!user.isPresent()) {
                logger.error("User record not found.");
                throw new NotFoundException("User record not found.");
            }
            User fetchedUser = user.get();
            logger.info(" User record with ID = {} is fetched successfully.", fetchedUser.getUserId());
            return fetchedUser;
        } catch (NullValueFound e) {
            throw new NullValueFound("User ID is null.");
        } catch (NotFoundException e) {
            throw new NotFoundException("User record not found.");
        } catch (Exception e) {
            logger.error("Error while fetching user record by id. " + e);
            throw new UnknownException("Error while fetching user record by id. " + e.getCause());
        }
    }

    @Override
    public User updateUserRecord(String userId, User user) throws NotFoundException, UnknownException, DataConstraintVoilationException, DuplicateRecordsException {
        try {
            if (Objects.isNull(userId)) {
                logger.error("User ID is null");
                throw new NotFoundException("User ID is null.");
            }
            //userDuplicateRecordCheck(user);
            User userRecordById = fetchUserById(userId);
            userRecordById.setUsername(user.getUsername());
            userRecordById.setPassword(user.getPassword());
            userRecordById.setFirstName(user.getFirstName());
            userRecordById.setLastName(user.getLastName());
            userRecordById.setMobileNumber(user.getMobileNumber());
            userRecordById.setEmail(user.getEmail());
            userRecordById.setMobileVerified(user.getMobileVerified());
            userRecordById.setEmailVerified(user.getEmailVerified());
            userRecordById.setLastPassword1(user.getLastPassword1());
            userRecordById.setLastPassword2(user.getLastPassword2());
            userRecordById.setLastPassword3(user.getLastPassword3());
            userRecordById.setUserType(user.getUserType());
            User updatedUserRecord = userRepository.save(userRecordById);
            logger.info("User record with ID = {} is updated succesfully.",updatedUserRecord.getUserId());
            return updatedUserRecord;
        } catch (NotFoundException e) {
            throw new NotFoundException("User ID is null.");
        } catch (DataIntegrityViolationException e) {
            logger.error("Error while updating user record : " + e.getRootCause());
            throw new DataConstraintVoilationException(e.getRootCause());
        } /*catch (DuplicateRecordsException e) {
            logger.error("Error while saving user record : " + e.getMessage());
            throw new DuplicateRecordsException(e.getMessage());
        } */catch (Exception e) {
            throw new UnknownException("Error while updating user record." + e.getCause());
        }
    }

    @Override
    public void deleteUserById(String userId) throws NotFoundException, NullValueFound, UnknownException {
        try {
            if (Objects.isNull(userId)) {
                logger.error("User ID is null");
                throw new NullValueFound("User ID is null.");
            }
        Optional<User> userRecordById = userRepository.findById(userId);
        if (!userRecordById.isPresent()) {
            logger.error("User record not found.");
            throw new NotFoundException("User record not found.");
        }
        User user = userRecordById.get();
        userRepository.delete(user);
        userMappingService.deleteUserMappingByIdBasedOnUserType(user.getUserId(), user.getUserType());
        logger.info("User record with id = {} is deleted successfully.", userId);
    }
        catch (NullValueFound e){
            throw new NullValueFound("User ID is null.");
        }
        catch (NotFoundException e){
            throw new NotFoundException("User record not found.");
        }
        catch (Exception e){
            throw new UnknownException("Error while deleting user record." + e.getCause());
        }


}
    private void userDuplicateRecordCheck(User user) throws DuplicateRecordsException {
        if (getDuplicateUserRecords(user) > 0){
            throw new DuplicateRecordsException("Duplicate records found");
        } /*else if (getDuplicateUserRecordsByEmail(user) > 0 && getDuplicateUserRecordsByPhoneNumber(user) > 0 ){
            throw new DuplicateRecordsException("Email and mobile number are already registered with us.");
        } else if (getDuplicateUserRecordsByEmail(user) > 0 && getDuplicateUserRecordsByUsername(user) > 0 ){
            throw new DuplicateRecordsException("Username and email are already registered with us.");
        } else if (getDuplicateUserRecordsByUsername(user) > 0 && getDuplicateUserRecordsByPhoneNumber(user) > 0 ){
            throw new DuplicateRecordsException("Username and mobile number are already registered with us.");
        }else if (getDuplicateUserRecordsByEmail(user) > 0 ){
            throw new DuplicateRecordsException("Email is already registered with us.");
        } else if (getDuplicateUserRecordsByPhoneNumber(user) > 0){
            throw new DuplicateRecordsException("Phone Number is " +
                    "already registered with us.");
        } else if (getDuplicateUserRecordsByUsername(user) > 0){
            throw new DuplicateRecordsException("Username is " +
                    "already registered with us.");
        }*/
    }

    private Integer getDuplicateUserRecords(User user) {
        return (userRepository.getDuplicateUserRecords(user.getUsername(),user.getMobileNumber(),user.getEmail(), user.getUserType()));
    }

    private Integer getDuplicateUserRecordsByUserAndMobileNumberAndEmail(User user) {
        return (userRepository.getDuplicateUserRecordsByUserAndMobileNumberAndEmail(user.getUsername(),user.getMobileNumber(),user.getEmail()));
    }

    private Integer getDuplicateUserRecordsByPhoneNumber(User user) {
        return (userRepository.getDuplicateUserRecordsByPhoneNumber(user.getMobileNumber()));
    }

    private Integer getDuplicateUserRecordsByEmail(User user) {
        return (userRepository.getDuplicateUserRecordsByEmail(user.getEmail()));
    }

    private Integer getDuplicateUserRecordsByUsername(User user) {
        return (userRepository.getDuplicateUserRecordsByUsername(user.getUsername()));
    }

    private Integer getDuplicateUserRecordsByUsernameAndMobileNumberAndUserType(User user) {
        return (userRepository.getDuplicateUserRecordsByUsernameAndMobileNumberAndUserType(user.getUsername(),user.getMobileNumber(),user.getUserType()));
    }

    private Integer getDuplicateUserRecordsByUsernameAndMobileNumber(User user) {
        return (userRepository.getDuplicateUserRecordsByUsernameAndMobileNumber(user.getUsername(),user.getMobileNumber()));
    }

    private Integer getDuplicateUserRecordsByUsernameAndEmailAndUserType(User user) {
        return (userRepository.getDuplicateUserRecordsByUsernameAndEmailAndUserType(user.getUsername(),user.getEmail(),user.getUserType()));
    }

    private Integer getDuplicateUserRecordsByUsernameAndEmail(User user) {
        return (userRepository.getDuplicateUserRecordsByUsernameAndEmail(user.getUsername(),user.getEmail()));
    }

    private Integer getDuplicateUserRecordsByUsernameAndUserType(User user) {
        return (userRepository.getDuplicateUserRecordsByUsernameAndUserType(user.getUsername(),user.getUserType()));
    }

    private Integer getDuplicateUserRecordsByMobileAndEmailAndUserType(User user) {
        return (userRepository.getDuplicateUserRecordsByMobileAndEmailAndUserType(user.getMobileNumber(),user.getEmail(),user.getUserType()));
    }

    private Integer getDuplicateUserRecordsByMobileAndEmail(User user) {
        return (userRepository.getDuplicateUserRecordsByMobileAndEmail(user.getMobileNumber(),user.getEmail()));
    }

    private Integer getDuplicateUserRecordsByMobileAndUserType(User user) {
        return (userRepository.getDuplicateUserRecordsByMobileAndUserType(user.getMobileNumber(),user.getUserType()));
    }

    private Integer getDuplicateUserRecordsByEmailAndUserType(User user) {
        return (userRepository.getDuplicateUserRecordsByEmailAndUserType(user.getEmail(),user.getUserType()));
    }

    private Integer getDuplicateUserRecordsByUserType(User user) {
        return (userRepository.getDuplicateUserRecordsByUserType(user.getUserType()));
    }

}
