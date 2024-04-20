package com.api.autiscare.service;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.Therapist;
import com.api.autiscare.entity.TherapyCenter;
import com.api.autiscare.exception.*;

import java.util.List;

public interface TherapistService {
    public Therapist save(Therapist therapist) throws NotFoundException, DataConstraintVoilationException, DuplicateRecordsException, UnknownException, NullValueFound;

    public List<Therapist> fetchAllTherapist();

    public Therapist fetchTherapistById(String theparistId) throws NotFoundException;

    public void deleteTheraphist(String theparistId) throws NotFoundException, NullValueFound, UnknownException;
}
