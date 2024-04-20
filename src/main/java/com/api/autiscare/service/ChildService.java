package com.api.autiscare.service;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.Child;
import com.api.autiscare.exception.*;

import java.util.List;

public interface ChildService {
        public Child save(Child child) throws NotFoundException, DataConstraintVoilationException, UnknownException, DuplicateRecordsException;;

        public List<Child> fetchAllChild();

        public Child fetchChildById(String childId) throws NotFoundException;

        public Child updateChildRecord(String childId, Child child) throws NotFoundException, DuplicateRecordsException, DataConstraintVoilationException, UnknownException;

        public void deleteChildById(String childId) throws NotFoundException, UnknownException, NullValueFound;
}
