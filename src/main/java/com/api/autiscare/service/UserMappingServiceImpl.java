package com.api.autiscare.service;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.User;
import com.api.autiscare.entity.UserMapping;
import com.api.autiscare.exception.DataConstraintVoilationException;
import com.api.autiscare.exception.NotFoundException;
import com.api.autiscare.exception.NullValueFound;
import com.api.autiscare.exception.UnknownException;
import com.api.autiscare.repository.UserMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserMappingServiceImpl implements UserMappingService {

    @Autowired
    UserMappingRepository userMappingRepository;

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserMappingServiceImpl.class);

    @Override
    public UserMapping save(UserMapping userMapping) throws NotFoundException, UnknownException, NullValueFound {
        try {
            if(null == userMapping.getUserType() || userMapping.getUserType().isBlank() || userMapping.getUserType().isEmpty()){
                User user = userService.fetchUserById(userMapping.getUserId());
                userMapping.setUserType(user.getUserType());
            }
            UserMapping persistedUserMapping = userMappingRepository.save(userMapping);
            logger.info("User Mapping record with ID = {} is saved successfully.", persistedUserMapping.getId());
            return persistedUserMapping;
        } catch (NullValueFound e) {
            throw new NullValueFound("User ID is null.");
        } catch (Exception e) {
            logger.error("Error while saving user mapping record : " + e);
            throw new UnknownException(e.getCause());
        }
    }

    @Override
    public List<UserMapping> fetchAllUserMappingRecords() throws UnknownException {
        try {
            List<UserMapping> fetchedUserMappingRecords = userMappingRepository.findAll();
            logger.info("User Mapping records are fetched successfully.");
            return fetchedUserMappingRecords;
        } catch (Exception e) {
            logger.error("Error while fetching all the user mapping records : " + e);
            throw new UnknownException("Error while fetching all the user mapping records" + e.getCause());
        }
    }

    @Override
    public UserMapping fetchUserMappingRecordsBasedOnUserId(String userId) throws UnknownException, NullValueFound {
        try{
            if (Objects.isNull(userId)) {
                logger.error("User ID is null");
                throw new NullValueFound("User ID is null.");
            }
            List<UserMapping> userMappingListBasedOnUserId = userMappingRepository.findByUserId(userId);
            if(userMappingListBasedOnUserId.size() > 0){
                return userMappingListBasedOnUserId.get(0);
            } else {
                return null;
            }

        }
        catch (NullValueFound e) {
            logger.error("User ID is null : " + e);
            throw new NullValueFound("User ID is null.");
        }
        catch (Exception e) {
            logger.error("Error while fetching the user mapping record based on userId : " + e);
            throw new UnknownException("Error while fetching the user mapping record based on userId : " + e.getCause());
        }
    }

    @Override
    public UserMapping fetchUserMappingRecordById(String userMappingId) throws NotFoundException, NullValueFound, UnknownException {
        try {
            if (Objects.isNull(userMappingId)) {
                logger.error("User mapping ID is null");
                throw new NullValueFound("User mapping ID is null.");
            }
            Optional<UserMapping> userMappingRecord = userMappingRepository.findById(userMappingId);
            if (!userMappingRecord.isPresent()) {
                logger.error("User mapping record not found.");
                throw new NotFoundException("User mapping record not found.");
            }
            UserMapping fetchedUserMappingRecord = userMappingRecord.get();
            logger.info(" User mapping record with ID = {} is fetched successfully.", fetchedUserMappingRecord.getId());
            return fetchedUserMappingRecord;
        } catch (NullValueFound e) {
            throw new NullValueFound("User mapping ID is null.");
        } catch (NotFoundException e) {
            throw new NotFoundException("User mapping record not found.");
        } catch (Exception e) {
            logger.error("Error while fetching user mapping record by id. " + e);
            throw new UnknownException("Error while fetching user mapping record by id. " + e.getCause());
        }
    }

    @Override
    public UserMapping updateUserMappingRecord(String userMappingId, UserMapping userMapping) throws NotFoundException, DataConstraintVoilationException, UnknownException {
        try {
            if (Objects.isNull(userMappingId)) {
                logger.error("User mapping ID is null");
                throw new NotFoundException("User mapping ID is null.");
            }
            UserMapping userMappingRecordById = fetchUserMappingRecordById(userMappingId);
            userMappingRecordById.setUserId(userMapping.getUserId());
            userMappingRecordById.setUserType(userMapping.getUserType());
            userMappingRecordById.setCentreId(userMapping.getCentreId());
            userMappingRecordById.setTherapistId(userMapping.getTherapistId());
            userMappingRecordById.setChildId(userMapping.getChildId());
            UserMapping userMappingRecord = userMappingRepository.save(userMappingRecordById);
            logger.info("User mapping record with ID = {} is updated succesfully.",userMappingRecord.getId());
            return userMappingRecord;
        } catch (NotFoundException e) {
            throw new NotFoundException("User mapping ID is null.");
        } catch (DataIntegrityViolationException e) {
            logger.error("Error while updating user mapping record : " + e);
            throw new DataConstraintVoilationException(e.getRootCause());
        } catch (Exception e) {
            throw new UnknownException("Error while updating user mapping record : " + e.getCause());
        }
    }

    public void updateChildIdInUserMappingRecordBasedOnUserId(String childId, String userId){
        List<UserMapping> listOfChildWithUserId = userMappingRepository.findAllByUserId(userId);
        System.out.println("listOfChildWithUserId = " +  listOfChildWithUserId);
        Iterator<UserMapping> userMappingIterator  = listOfChildWithUserId.iterator();
        while(userMappingIterator.hasNext()){
            UserMapping userMappingRecord = userMappingIterator.next();
            System.out.println("User Mapping Record = "+ userMappingRecord);
            if (null == userMappingRecord.getChildId()){
                userMappingRecord.setChildId(childId);
                userMappingRepository.save(userMappingRecord);
            }else{
                UserMapping newUserMappingRecord = new UserMapping();
                newUserMappingRecord.setUserId(userId);
                newUserMappingRecord.setCentreId(userMappingRecord.getCentreId());
                newUserMappingRecord.setChildId(childId);
                newUserMappingRecord.setTherapistId(userMappingRecord.getTherapistId());
                newUserMappingRecord.setUserType(userMappingRecord.getUserType());
                userMappingRepository.save(newUserMappingRecord);
            }
        }

    }

    @Override
    public void deleteUserMappingById(String userMappingId) throws NotFoundException, NullValueFound, UnknownException {
        try {
            if (Objects.isNull(userMappingId)) {
                logger.error("User mapping ID is null");
                throw new NullValueFound("User mapping ID is null.");
            }
            Optional<UserMapping> userMappingRecordById = userMappingRepository.findById(userMappingId);
            if (!userMappingRecordById.isPresent()) {
                logger.error("User mapping record not found.");
                throw new NotFoundException("User mapping record not found.");
            }
            userMappingRepository.delete(userMappingRecordById.get());
            logger.info("User mapping record with id = {} is deleted successfully.", userMappingId);
        }
        catch (NullValueFound e){
            throw new NullValueFound("User mapping ID is null.");
        }
        catch (NotFoundException e){
            throw new NotFoundException("User mapping record not found.");
        }
        catch (Exception e){
            throw new UnknownException("Error while deleting user mapping record." + e.getCause());
        }
    }

    @Override
    public void deleteUserMappingByIdBasedOnUserType(String id, String userType) throws NotFoundException, NullValueFound, UnknownException {
        try{
            if (id.isEmpty()) {
                logger.error(userType + " ID is null");
                throw new NullValueFound(userType + " ID is null");
            }
        if (userType.equals("C")){
            List<UserMapping> listOfUserMappingRecordsByChildId = userMappingRepository.findAllByChildId(id);
            if(!listOfUserMappingRecordsByChildId.isEmpty()) {
                userMappingRepository.deleteAll(listOfUserMappingRecordsByChildId);
            }else {
                // else part is to support user deletion
                List<UserMapping> listOfUserMappingRecordsByUserId = userMappingRepository.findAllByUserId(id);
                if(!listOfUserMappingRecordsByUserId.isEmpty()) {
                    userMappingRepository.deleteAll(listOfUserMappingRecordsByUserId);
                }else {
                    throw new NotFoundException("No records found to be deleted.");
                }
            }
        } else if (userType.equals("TC")){
            List<UserMapping> listOfUserMappingRecordsByCenterId = userMappingRepository.findAllByCentreId(id);
            if(!listOfUserMappingRecordsByCenterId.isEmpty()) {
                userMappingRepository.deleteAll(listOfUserMappingRecordsByCenterId);
            }else {
                // else part is to support user deletion
                List<UserMapping> listOfUserMappingRecordsByUserId = userMappingRepository.findAllByUserId(id);
                if(!listOfUserMappingRecordsByUserId.isEmpty()) {
                    userMappingRepository.deleteAll(listOfUserMappingRecordsByUserId);
                }else {
                    throw new NotFoundException("No records found to be deleted.");
                }
            }
        }else   if (userType.equals("T")){
            List<UserMapping> listOfUserMappingRecordsByTheraphistId = userMappingRepository.findAllByTherapistId(id);
            if(!listOfUserMappingRecordsByTheraphistId.isEmpty()) {
                userMappingRepository.deleteAll(listOfUserMappingRecordsByTheraphistId);
            }else {
                // else part is to support user deletion
                List<UserMapping> listOfUserMappingRecordsByUserId = userMappingRepository.findAllByUserId(id);
                if(!listOfUserMappingRecordsByUserId.isEmpty()) {
                    userMappingRepository.deleteAll(listOfUserMappingRecordsByUserId);
                }else {
                    throw new NotFoundException("No records found to be deleted.");
                }
            }
        }
        }
        catch (NotFoundException e){
            throw new NotFoundException("No records found to be deleted.");
        }
        catch (NullValueFound e){
            throw new NullValueFound("userType " + " ID is null.");
        }
        catch (Exception e){
            throw new UnknownException("Error while deleting user mapping record." + e.getCause());
        }

    }
}
