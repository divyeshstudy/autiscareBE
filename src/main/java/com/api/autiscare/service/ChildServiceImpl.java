package com.api.autiscare.service;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.Child;
import com.api.autiscare.entity.User;
import com.api.autiscare.entity.UserMapping;
import com.api.autiscare.exception.*;
import com.api.autiscare.repository.ChildReporsitory;
import com.api.autiscare.repository.UserMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ChildServiceImpl implements ChildService{

    private static final Logger logger = LoggerFactory.getLogger(ChildServiceImpl.class);

    @Autowired
    private ChildReporsitory childReporsitory;

    @Autowired
    private UserMappingService userMappingService;

    @Override
    public Child save(Child child) throws DataConstraintVoilationException, UnknownException, DuplicateRecordsException {
        try {
            childDuplicateRecordCheck(child);
            Child persistedChild = childReporsitory.save(child);
            UserMapping userMapping = new UserMapping();
            userMapping.setChildId(persistedChild.getChildId());
            userMapping.setUserId(persistedChild.getUser().getUserId());
            userMappingService.save(userMapping);
            return persistedChild;
        }
        catch (DataConstraintVoilationException e){
            throw new DataConstraintVoilationException(e.getMessage());
        }
        catch (DuplicateRecordsException e){
            throw new DuplicateRecordsException(e.getMessage());
        }
        catch (Exception e) {
            throw new UnknownException(e.getCause());
        }
    }

    @Override
    public List<Child> fetchAllChild() {
        return childReporsitory.findAll();
    }

    @Override
    public Child fetchChildById(String childId) throws NotFoundException {
        Optional<Child> child = childReporsitory.findById(childId);
        if(!child.isPresent()){
            throw new NotFoundException("Child Id Not Found");
        }
        return child.get();
    }

    @Override
    public Child updateChildRecord(String childId, Child child) throws NotFoundException, DuplicateRecordsException, DataConstraintVoilationException, UnknownException {
        try {
            if (Objects.isNull(childId)) {
                logger.error("Child ID is null");
                throw new NotFoundException("Child ID is null.");
            }
            User user = new User();
            user.setUserId(child.getUser().getUserId());
            childDuplicateRecordCheck(child);
            Child childRecordById = fetchChildById(childId);
            childRecordById.setFirstName(child.getFirstName());
            childRecordById.setLastName(child.getLastName());
            childRecordById.setAge(child.getAge());
            childRecordById.setGender(child.getGender());
            childRecordById.setMotherName(child.getMotherName());
            childRecordById.setMotherAge(child.getMotherAge());
            childRecordById.setMotherMobile(child.getMotherMobile());
            childRecordById.setMotherEmail(child.getMotherEmail());
            childRecordById.setMotherOccupation(child.getMotherOccupation());
            childRecordById.setFatherName(child.getFatherName());
            childRecordById.setFatherAge(child.getFatherAge());
            childRecordById.setFatherMobile(child.getFatherMobile());
            childRecordById.setFatherEmail(child.getFatherEmail());
            childRecordById.setFatherOccupation(child.getFatherOccupation());
            childRecordById.setAddress1(child.getAddress1());
            childRecordById.setAddress2(child.getAddress2());
            childRecordById.setCity(child.getCity());
            childRecordById.setState(child.getState());
            childRecordById.setPincode(child.getPincode());
            childRecordById.setPhoto(child.getPhoto());
            childRecordById.setUser(user);
            Child updatedChildRecord = childReporsitory.save(childRecordById);
            logger.info("Child record with ID = {} is updated succesfully.", childRecordById.getChildId());
            return updatedChildRecord;
        }
        catch (NotFoundException e) {
            throw new NotFoundException("Child ID is null.");
        } catch (DataIntegrityViolationException e) {
            logger.error("Error while updating child record : " + e.getRootCause());
            throw new DataConstraintVoilationException(e.getRootCause());
        } catch (DuplicateRecordsException e) {
            logger.error("Error while saving child record : " + e.getMessage());
            throw new DuplicateRecordsException(e.getMessage());
        } catch (Exception e) {
            throw new UnknownException("Error while updating child record." + e.getCause());
        }
    }

    @Override
    public void deleteChildById(String childId) throws NotFoundException, UnknownException, NullValueFound {
        try {
            Optional<Child> childById = childReporsitory.findById(childId);
            if (!childById.isPresent()) {
                throw new NotFoundException("Child Id Not Found");
            }
            if (Objects.nonNull(childById) &&
                    !"".equalsIgnoreCase(childId)) {
                childReporsitory.delete(childById.get());
            }else{
                throw new NullValueFound();
            }
            userMappingService.deleteUserMappingByIdBasedOnUserType(childId, "C");
        }
        catch (NullValueFound e){
            throw new NullValueFound("Child Id is null.");
        }
        catch (NotFoundException e) {
            throw new NotFoundException("No records found with the child id = " + childId);
        }
        catch (Exception e) {
            throw new UnknownException("Error while deleting child record." + e.getCause());
        }

    }

    private void childDuplicateRecordCheck(Child child) throws DuplicateRecordsException {
        int value = getDuplicateChildRecordsByCompositeKeys(child.getFirstName(),child.getLastName(),child.getMotherMobile(),child.getFatherMobile());
        if ( value > 0){
            throw new DuplicateRecordsException("Duplicate records found");
        } else if (getDuplicateChildRecordsByFirstNameAndLastName(child.getFirstName(),child.getLastName()) > 0 ){
            throw new DuplicateRecordsException("Child with firstname and last name is already registered with us.");
        } else if (getDuplicateChildRecordsByMotherMobile(child.getMotherMobile()) > 0) {
                throw new DuplicateRecordsException("Child's mother mobile number is already registered with us.");
            } else if (getDuplicateChildRecordsByFatherMobile(child.getFatherMobile()) > 0) {
                throw new DuplicateRecordsException("Child's father mobile number is already registered with us.");
            }
    }

    private Integer getDuplicateChildRecordsByFirstNameAndLastName(String firstName, String lastName) {
        List <Child> duplicateChildRecordsByFirstName = childReporsitory.findAllByFirstNameAndLastName(firstName, lastName);
        return duplicateChildRecordsByFirstName.size();
    }

    private Integer getDuplicateChildRecordsByMotherMobile(String motherMobile) {
        List <Child> duplicateChildRecordsByMotherMobile = childReporsitory.findAllByMotherMobile(motherMobile);
        return duplicateChildRecordsByMotherMobile.size();
    }

    private Integer getDuplicateChildRecordsByFatherMobile(String fatherMobile) {
        List <Child> duplicateChildRecordsByFatherMobile = childReporsitory.findAllByFatherMobile(fatherMobile);
        return duplicateChildRecordsByFatherMobile.size();
    }

    private Integer getDuplicateChildRecordsByCompositeKeys(String firstName, String lastName, String motherMobile, String fatherMobile) {
        List <Child> duplicateChildRecordsByByCompositeKeys = childReporsitory.findAllByFirstNameAndLastNameAndMotherMobileAndFatherMobile(firstName, lastName, motherMobile, fatherMobile);
        return duplicateChildRecordsByByCompositeKeys.size();
    }

}
