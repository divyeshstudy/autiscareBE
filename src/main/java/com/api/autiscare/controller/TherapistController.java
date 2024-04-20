package com.api.autiscare.controller;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.Therapist;
import com.api.autiscare.exception.*;
import com.api.autiscare.service.TherapistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autiscare")
public class TherapistController {

    @Autowired
    private TherapistService therapistService;

    private static final Logger logger = LoggerFactory.getLogger(Therapist.class);

    @PostMapping("/therapist/register")
    public ResponseEntity<Therapist> register(@RequestBody Therapist therapist) throws NotFoundException, DataConstraintVoilationException, DuplicateRecordsException, NullValueFound, UnknownException {
            Therapist therapistResponse = therapistService.save(therapist);
            logger.info("Therapist with ID = %d is registered."+ therapistResponse.getId());
            return new ResponseEntity<Therapist>(therapistResponse, HttpStatus.OK);
    }

    @GetMapping("/therapist")
    public ResponseEntity<List<Therapist>> fetchAllTherapist(){
        List<Therapist> listOfTherapist = therapistService.fetchAllTherapist();
        logger.info("therapist list are fetched successfully");
        return new ResponseEntity<List<Therapist>>(listOfTherapist, HttpStatus.OK);
    }

    @GetMapping("/therapist/{id}")
    public ResponseEntity<Therapist> fetchTherapistById(@PathVariable ("id") String theparistId ) throws NotFoundException {
        Therapist therapistResponse = therapistService.fetchTherapistById(theparistId);
        logger.info("Theraphist with id = %d is fetched successfully." + therapistResponse.getId());
        return new ResponseEntity<Therapist>(therapistResponse, HttpStatus.OK);
    }

    @DeleteMapping("/therapist/{id}")
    public ResponseEntity<String> deleteTherapistById(@PathVariable ("id") String theparistId) throws NotFoundException, UnknownException, NullValueFound {
        therapistService.deleteTheraphist(theparistId);
        logger.info("Therapist with id = %d is deleted successfully." + theparistId);
        return new ResponseEntity<String>("Therapist Id : " + theparistId +" is deleted successfully.", HttpStatus.OK);

    }

}