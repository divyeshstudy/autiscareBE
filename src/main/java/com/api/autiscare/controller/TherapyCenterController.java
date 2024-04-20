package com.api.autiscare.controller;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.TherapyCenter;
import com.api.autiscare.exception.*;
import com.api.autiscare.service.TherapyCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autiscare")
public class TherapyCenterController {

    @Autowired
    private TherapyCenterService therapyCenterService;

    private static final Logger logger = LoggerFactory.getLogger(TherapyCenterController.class);

    @PostMapping("/therapy-center/register")
    public ResponseEntity<TherapyCenter> register(@RequestBody TherapyCenter therapyCenter) throws NotFoundException, DataConstraintVoilationException, UnknownException, DuplicateRecordsException {
        TherapyCenter therapyCenterResponse = therapyCenterService.save(therapyCenter);
        logger.info("Theraphy Center with ID = %d is registered."+ therapyCenterResponse.getCenterId());
        return new ResponseEntity<TherapyCenter>(therapyCenterResponse, HttpStatus.OK);
    }

    @GetMapping("/therapy-center")
    public ResponseEntity<List<TherapyCenter>> fetchTherapyCenter(){
        List<TherapyCenter> listOfTherapyCenter = therapyCenterService.fetchTheparistCenter();
        logger.info("Theraphy Centers list are fetched successfully");
        return new ResponseEntity<List<TherapyCenter>>(listOfTherapyCenter, HttpStatus.OK);

    }

    @GetMapping("/therapy-center/{id}")
    public ResponseEntity<TherapyCenter> fetchTherapyCenterById(@PathVariable ("id") String theparistCenterId ) throws NotFoundException {
        System.out.println("theparistCenterId is = "+ theparistCenterId);
        TherapyCenter therapyCenterResponse = therapyCenterService.fetchTheparistCenterById(theparistCenterId);
        logger.info("Theraphy Center with id = %d is fetched successfully." + therapyCenterResponse.getCenterId());
        return new ResponseEntity<TherapyCenter>(therapyCenterResponse, HttpStatus.OK);

    }

    @PutMapping("/therapy-center/{id}")
    public ResponseEntity<TherapyCenter> updateTherapyCenter(@PathVariable ("id") String theparistCenterId, @RequestBody TherapyCenter therapyCenter) throws NotFoundException, DuplicateRecordsException, DataConstraintVoilationException, UnknownException {
        //return therapyCenterService.updateTheparistCenter(theparistCenterId, therapyCenter);
        TherapyCenter therapyCenterResponse = therapyCenterService.updateTheparistCenter(theparistCenterId, therapyCenter);
        return new ResponseEntity<TherapyCenter>(therapyCenterResponse, HttpStatus.OK);

    }

    @DeleteMapping("/therapy-center/{id}")
    public ResponseEntity<String> deleteTherapyCenter(@PathVariable ("id") String theparistCenterId) throws NotFoundException, UnknownException, NullValueFound {
        therapyCenterService.deleteTheparistCenter(theparistCenterId);
        logger.info("Theraphy Center with id = %d is deleted successfully." + theparistCenterId);
        return new ResponseEntity<String>("Theraphy Center Id : " + theparistCenterId +" is deleted successfully.", HttpStatus.OK);

    }

}
