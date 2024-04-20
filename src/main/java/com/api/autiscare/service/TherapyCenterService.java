package com.api.autiscare.service;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.TherapyCenter;
import com.api.autiscare.exception.*;

import java.util.List;

public interface TherapyCenterService {
    public TherapyCenter save(TherapyCenter therapyCenter) throws NotFoundException, DataConstraintVoilationException, DuplicateRecordsException, UnknownException;

    public List<TherapyCenter> fetchTheparistCenter();

    public TherapyCenter fetchTheparistCenterById(String theparistCenterId) throws NotFoundException;

    public TherapyCenter updateTheparistCenter(String theparistCenterId, TherapyCenter therapyCenter) throws NotFoundException, DataConstraintVoilationException, DuplicateRecordsException, UnknownException;

    public void deleteTheparistCenter(String theparistCenterId) throws NotFoundException, NullValueFound, UnknownException;
}
