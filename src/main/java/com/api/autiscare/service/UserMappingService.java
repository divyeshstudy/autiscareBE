package com.api.autiscare.service;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.UserMapping;
import com.api.autiscare.exception.DataConstraintVoilationException;
import com.api.autiscare.exception.NotFoundException;
import com.api.autiscare.exception.NullValueFound;
import com.api.autiscare.exception.UnknownException;

import java.util.List;

public interface UserMappingService {
    public UserMapping save(UserMapping child) throws NotFoundException, DataConstraintVoilationException, UnknownException, NullValueFound;;

    public List<UserMapping> fetchAllUserMappingRecords() throws UnknownException;

    public UserMapping fetchUserMappingRecordsBasedOnUserId(String userId) throws UnknownException, NullValueFound;

    public UserMapping fetchUserMappingRecordById(String userMappingId) throws NotFoundException, NullValueFound, UnknownException;

    public UserMapping updateUserMappingRecord(String userMappingId, UserMapping userMapping) throws NotFoundException, DataConstraintVoilationException, UnknownException;

    public void deleteUserMappingById(String userMappingId) throws NotFoundException, NullValueFound, UnknownException;

    public void deleteUserMappingByIdBasedOnUserType(String Id,String userType) throws NotFoundException, NullValueFound, UnknownException;

}
