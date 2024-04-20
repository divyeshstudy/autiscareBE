package com.api.autiscare.service;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.*;
import com.api.autiscare.exception.*;
import com.api.autiscare.repository.TherapistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TherapistServiceImpl implements TherapistService {

    @Autowired
    private TherapistRepository therapistRepository;

    @Autowired
    private UserMappingService userMappingService;

    @Autowired
    UserService userService;

    @Override
    public Therapist save(Therapist therapist) throws DataConstraintVoilationException, NotFoundException, NullValueFound, UnknownException, DuplicateRecordsException {
        try {
            therapistDuplicateRecordCheck(therapist);
            Therapist persistedTherapist = therapistRepository.save(therapist);
            UserMapping userMapping = new UserMapping();
            userMapping.setTherapistId(persistedTherapist.getId());
            userMapping.setUserId(persistedTherapist.getUser().getUserId());
            userMappingService.save(userMapping);
            return persistedTherapist;
        }
        catch (DuplicateRecordsException e){
            throw new DuplicateRecordsException(e.getMessage());
        }
          catch (DataConstraintVoilationException e) {
            throw new DataConstraintVoilationException(e.getMessage());
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (NullValueFound e) {
            throw new NullValueFound(e.getMessage());
        } catch (Exception e) {
            throw new UnknownException(e.getCause());
        }

}

    @Override
    public List<Therapist> fetchAllTherapist() {
        return therapistRepository.findAll();
    }

    @Override
    public Therapist fetchTherapistById(String theparistId) throws NotFoundException {
        Optional<Therapist> therapist = therapistRepository.findById(theparistId);
        if(!therapist.isPresent()){
            throw new NotFoundException("Theraphist Not Found");
        }
        return therapist.get();
    }

    @Override
    public void deleteTheraphist(String theparistId) throws NotFoundException, NullValueFound, UnknownException {
        try {
            Optional<Therapist> therapistById = therapistRepository.findById(theparistId);
            if (!therapistById.isPresent()) {
                throw new NotFoundException("Therapist Id Not Found");
            }
            if (Objects.nonNull(therapistById) &&
                    !"".equalsIgnoreCase(theparistId)) {
                therapistRepository.delete(therapistById.get());
            }else{
                throw new NullValueFound();
            }
            userMappingService.deleteUserMappingByIdBasedOnUserType(theparistId, "T");
        }
        catch (NullValueFound e){
            throw new NullValueFound("Therapist Id is null.");
        }
        catch (NotFoundException e) {
            throw new NotFoundException("No records found with the Therapist id = " + theparistId);
        }
        catch (Exception e) {
            throw new UnknownException("Error while deleting Therapist record." + e.getCause());
        }
        /*Optional<Therapist> theparistById = therapistRepository.findById(theparistId);
        if(!theparistById.isPresent()){
            throw new NotFoundException("Theraphist Not Found");
        }
        if(Objects.nonNull(theparistById)){
            therapistRepository.delete(theparistById.get());
        }*/
    }

    private void therapistDuplicateRecordCheck(Therapist therapist) throws DuplicateRecordsException {
        int value = getDuplicateTherapistRecordsByCompositeKeys(therapist.getFirstName(),therapist.getLastName(),therapist.getPhone(),therapist.getEmail());
        System.out.println("value = " + value);
        if ( value > 0){
            throw new DuplicateRecordsException("Duplicate records found");
        }

    }

    private int getDuplicateTherapistRecordsByCompositeKeys(String firstName, String lastName, String phone, String email) {
        List <Therapist> getDuplicateTherapistRecordsByCompositeKeys = therapistRepository.findAllByFirstNameAndLastNameAndPhoneAndEmail(firstName, lastName, phone, email);
        return getDuplicateTherapistRecordsByCompositeKeys.size();
    }
}
