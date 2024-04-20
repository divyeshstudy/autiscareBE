package com.api.autiscare.controller;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.Child;
import com.api.autiscare.exception.*;
import com.api.autiscare.service.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autiscare")
public class ChildController {

    @Autowired
    private ChildService childService;

    @PostMapping("/child/register")
    public ResponseEntity<Child> register(@RequestBody Child child) throws DataConstraintVoilationException, DuplicateRecordsException, NotFoundException, UnknownException {

        Child childResponse = childService.save(child);
        return new ResponseEntity<Child>(childResponse, HttpStatus.OK);
    }

    @GetMapping("/child")
    public ResponseEntity<List<Child>> fetchAllChild(){
        List<Child> listOfChild = childService.fetchAllChild();
        return new ResponseEntity<List<Child>>(listOfChild, HttpStatus.OK);
    }

    @GetMapping("/child/{id}")
    public ResponseEntity<Child> fetchChildById(@PathVariable ("id") String childId ) throws NotFoundException {
        Child childResponse = childService.fetchChildById(childId);
        return new ResponseEntity<Child>(childResponse, HttpStatus.OK);
    }

    @DeleteMapping("/child/{id}")
    public ResponseEntity<String> deleteChild(@PathVariable ("id") String childId) throws NotFoundException, NullValueFound, UnknownException {
        childService.deleteChildById(childId);
        return new ResponseEntity<String>("Child Id : " + childId +" is deleted successfully.", HttpStatus.OK);
    }
}
