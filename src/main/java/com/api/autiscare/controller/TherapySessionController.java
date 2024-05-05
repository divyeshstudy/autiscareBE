package com.api.autiscare.controller;

import com.api.autiscare.entity.TherapySession;
import com.api.autiscare.exception.*;
import com.api.autiscare.service.TheraphySessionService;
import com.api.autiscare.service.TherapyCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author divyesh.dwivedi
 */

@RestController
@RequestMapping("/autiscare")
public class TherapySessionController {

    private static final Logger logger = LoggerFactory.getLogger(TherapySessionController.class);

    @Autowired
    private TheraphySessionService theraphySessionService;

    @PostMapping("/therapy-session/save")
    public ResponseEntity<TherapySession> save(@RequestBody TherapySession therapySession) throws NotFoundException, DataConstraintVoilationException, DuplicateRecordsException, NullValueFound, UnknownException {
        logger.info("therapy-session save");
        TherapySession therapySessionResponse = theraphySessionService.save(therapySession);
        logger.info("Therapy Session with ID = %d is saved successfully."+ therapySessionResponse.getTheraphySessionId());
        return new ResponseEntity<TherapySession>(therapySessionResponse, HttpStatus.OK);
    }
}
