package com.api.autiscare.service;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.Child;
import com.api.autiscare.entity.TherapyCenter;
import com.api.autiscare.entity.User;
import com.api.autiscare.entity.UserMapping;
import com.api.autiscare.exception.*;
import com.api.autiscare.repository.TherapistCenterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TherapyCenterServiceImpl implements TherapyCenterService {

    private static final Logger logger = LoggerFactory.getLogger(TherapyCenterServiceImpl.class);

    @Autowired
    private TherapistCenterRepository therapistCenterRepository;

    @Autowired
    UserMappingService userMappingService;

    @Autowired
    UserService userService;

    @Override
    public TherapyCenter save(TherapyCenter therapyCenter) throws DataConstraintVoilationException, DuplicateRecordsException, UnknownException {
        try {
            User user = userService.fetchUserById(therapyCenter.getUser().getUserId());
            if(!user.getUserType().equals("TC")){
                throw new Exception("Therapy Center user type mismatch.");
            }
            theraphyCenterDuplicateRecordCheck(therapyCenter);
            TherapyCenter persistedTherpahyCenter =  therapistCenterRepository.save(therapyCenter);
            UserMapping userMapping = new UserMapping();
            userMapping.setCentreId(persistedTherpahyCenter.getCenterId());
            userMapping.setUserId(persistedTherpahyCenter.getUser().getUserId());
            userMappingService.save(userMapping);
            return persistedTherpahyCenter;
        }
        catch (DataIntegrityViolationException e) {
            logger.error("Error while saving theraphy center record : " + e.getRootCause());
            throw new DataConstraintVoilationException(e.getRootCause());
        }
        catch (DuplicateRecordsException e) {
            logger.error("Error while saving theraphy center record : " + e.getMessage());
            throw new DuplicateRecordsException(e.getMessage());
        }catch (Exception e) {
            logger.error("Error while saving theraphy center record : " + e.getMessage());
            throw new UnknownException(e.getCause());
        }
    }

    @Override
    public List<TherapyCenter> fetchTheparistCenter() {
        return therapistCenterRepository.findAll();
    }

    @Override
    public TherapyCenter fetchTheparistCenterById(String theparistCenterId) throws NotFoundException {
        Optional<TherapyCenter> therapyCenter = therapistCenterRepository.findById(theparistCenterId);
        if(!therapyCenter.isPresent()){
            throw new NotFoundException("Theraphy Center Not Found");
        }
        return therapyCenter.get();
    }

    @Override
    public TherapyCenter updateTheparistCenter(String theparistCenterId, TherapyCenter therapyCenter) throws NotFoundException, DataConstraintVoilationException, DuplicateRecordsException, UnknownException {
        try {
            if (Objects.isNull(theparistCenterId)) {
                logger.error("Theraphy Center ID is null");
                throw new NotFoundException("Theraphy Center ID is null.");
            }
            User user = new User();
            user.setUserId(therapyCenter.getUser().getUserId());
            theraphyCenterDuplicateRecordCheck(therapyCenter);
            TherapyCenter therapyCenterRecordById = fetchTheparistCenterById(theparistCenterId);
            therapyCenterRecordById.setCenterName(therapyCenter.getCenterNumber());
            therapyCenterRecordById.setTheraphyType(therapyCenter.getTheraphyType());
            therapyCenterRecordById.setAddress1(therapyCenter.getAddress1());
            therapyCenterRecordById.setAddress2(therapyCenter.getAddress2());
            therapyCenterRecordById.setCity(therapyCenter.getCity());
            therapyCenterRecordById.setState(therapyCenter.getState());
            therapyCenterRecordById.setPin(therapyCenter.getPin());
            therapyCenterRecordById.setCenterEmail(therapyCenter.getCenterEmail());
            therapyCenterRecordById.setCenterNumber(therapyCenter.getCenterName());
            therapyCenterRecordById.setOwnerName(therapyCenter.getOwnerName());
            therapyCenterRecordById.setOwnerNumber(therapyCenter.getOwnerNumber());
            therapyCenterRecordById.setOwnerEmail(therapyCenter.getOwnerEmail());
            therapyCenterRecordById.setGstno(therapyCenter.getGstno());
            therapyCenterRecordById.setUser(user);
            TherapyCenter updatedTherapyCenterRecord = therapistCenterRepository.save(therapyCenterRecordById);
            logger.info("Theraphy Center record with ID = {} is updated succesfully.", updatedTherapyCenterRecord.getCenterId());
            return updatedTherapyCenterRecord;
        }
        catch (NotFoundException e) {
            throw new NotFoundException("Center ID is null.");
        } catch (DataIntegrityViolationException e) {
            logger.error("Error while updating theraphy center record : " + e.getRootCause());
            throw new DataConstraintVoilationException(e.getRootCause());
        } catch (DuplicateRecordsException e) {
            logger.error("Error while saving theraphy center record : " + e.getMessage());
            throw new DuplicateRecordsException(e.getMessage());
        } catch (Exception e) {
            throw new UnknownException("Error while updating theraphy center record." + e.getCause());
        }
    }

    @Override
    public void deleteTheparistCenter(String theparistCenterId) throws NotFoundException, NullValueFound, UnknownException {
        /*Optional<TherapyCenter> therapyCenterById = therapistCenterRepository.findById(theparistCenterId);
        if(!therapyCenterById.isPresent()){
            throw new NotFoundException("Theraphy Center Not Found");
        }
        if(Objects.nonNull(therapyCenterById)){
             therapistCenterRepository.delete(therapyCenterById.get());
        }*/
        try {
            Optional<TherapyCenter> therapyCenterById = therapistCenterRepository.findById(theparistCenterId);
            if (!therapyCenterById.isPresent()) {
                throw new NotFoundException("Theraphy Center Id Not Found");
            }
            if (Objects.nonNull(therapyCenterById) &&
                    !"".equalsIgnoreCase(theparistCenterId)) {
                therapistCenterRepository.delete(therapyCenterById.get());
            }else{
                throw new NullValueFound();
            }
            userMappingService.deleteUserMappingByIdBasedOnUserType(theparistCenterId, "TC");
        }
        catch (NullValueFound e){
            throw new NullValueFound("Theraohy Center Id is null.");
        }
        catch (NotFoundException e) {
            throw new NotFoundException("No records found with the theraphy center id = " + theparistCenterId);
        }
        catch (Exception e) {
            throw new UnknownException("Error while deleting theraphy center record." + e.getCause());
        }
    }

    private void theraphyCenterDuplicateRecordCheck(TherapyCenter therapyCenter) throws DuplicateRecordsException {
        if (getDuplicateUserBasedOnNameAndTypeAndNumberAndEmailRecords(therapyCenter) > 0){
            throw new DuplicateRecordsException("Duplicate records found");
        }




    }

    private Integer getDuplicateUserBasedOnNameAndTypeAndNumberAndEmailRecords(TherapyCenter therapyCenter) {
        return (therapistCenterRepository.getDuplicateUserBasedOnNameAndTypeAndNumberAndEmailRecords(therapyCenter.getCenterName(),therapyCenter.getTheraphyType(),therapyCenter.getCenterNumber(),therapyCenter.getCenterEmail()));
    }

    private Integer getDuplicateUserBasedOnNameAndTypeAndNumberRecords(TherapyCenter therapyCenter) {
        return (therapistCenterRepository.getDuplicateUserBasedOnNameAndTypeAndNumberRecords(therapyCenter.getCenterName(),therapyCenter.getTheraphyType(),therapyCenter.getCenterNumber()));
    }

    private Integer getDuplicateUserBasedOnNameAndTypeAndEmailRecords(TherapyCenter therapyCenter) {
        return (therapistCenterRepository.getDuplicateUserBasedOnNameAndTypeAndEmailRecords(therapyCenter.getCenterName(),therapyCenter.getTheraphyType(),therapyCenter.getCenterEmail()));
    }

    private Integer getDuplicateUserBasedOnNameAndTypeRecords(TherapyCenter therapyCenter) {
        return (therapistCenterRepository.getDuplicateUserBasedOnNameAndTypeRecords(therapyCenter.getCenterName(),therapyCenter.getTheraphyType()));
    }

    private Integer getDuplicateUserBasedOnNameAndNumberAndEmailRecords(TherapyCenter therapyCenter) {
        return (therapistCenterRepository.getDuplicateUserBasedOnNameAndNumberAndEmailRecords(therapyCenter.getCenterName(),therapyCenter.getCenterNumber(),therapyCenter.getCenterEmail()));
    }

    private Integer getDuplicateUserBasedOnNameAndNumberRecords(TherapyCenter therapyCenter) {
        return (therapistCenterRepository.getDuplicateUserBasedOnNameAndNumberRecords(therapyCenter.getCenterName(),therapyCenter.getCenterNumber()));
    }

    private Integer getDuplicateUserBasedOnNameAndEmailRecords(TherapyCenter therapyCenter) {
        return (therapistCenterRepository.getDuplicateUserBasedOnNameAndEmailRecords(therapyCenter.getCenterName(),therapyCenter.getCenterEmail()));
    }

    private Integer getDuplicateUserBasedOnTypeAndNumberAndEmailRecords(TherapyCenter therapyCenter) {
        return (therapistCenterRepository.getDuplicateUserBasedOnTypeAndNumberAndEmailRecords(therapyCenter.getTheraphyType(),therapyCenter.getCenterNumber(),therapyCenter.getCenterEmail()));
    }

    private Integer getDuplicateUserBasedOnTypeAndNumberRecords(TherapyCenter therapyCenter) {
        return (therapistCenterRepository.getDuplicateUserBasedOnTypeAndNumberRecords(therapyCenter.getTheraphyType(),therapyCenter.getCenterNumber()));
    }

    private Integer getDuplicateUserBasedOnTypeAndEmailRecords(TherapyCenter therapyCenter) {
        return (therapistCenterRepository.getDuplicateUserBasedOnTypeAndEmailRecords(therapyCenter.getTheraphyType(),therapyCenter.getCenterEmail()));
    }

    private Integer getDuplicateUserBasedOnNumberAndEmailRecords(TherapyCenter therapyCenter) {
        return (therapistCenterRepository.getDuplicateUserBasedOnNumberAndEmailRecords(therapyCenter.getCenterNumber(),therapyCenter.getCenterEmail()));
    }

    private Integer getDuplicateUserBasedOnNameRecords(TherapyCenter therapyCenter) {
        return (therapistCenterRepository.getDuplicateUserBasedOnNameRecords(therapyCenter.getCenterName()));
    }

    private Integer getDuplicateUserBasedOnTypeRecords(TherapyCenter therapyCenter) {
        return (therapistCenterRepository.getDuplicateUserBasedOnTypeRecords(therapyCenter.getTheraphyType()));
    }

    private Integer getDuplicateUserBasedOnNumberRecords(TherapyCenter therapyCenter) {
        return (therapistCenterRepository.getDuplicateUserBasedOnNumberRecords(therapyCenter.getCenterNumber()));
    }

    private Integer getDuplicateUserBasedOnEmailRecords(TherapyCenter therapyCenter) {
        return (therapistCenterRepository.getDuplicateUserBasedOnEmailRecords(therapyCenter.getCenterEmail()));
    }


}
