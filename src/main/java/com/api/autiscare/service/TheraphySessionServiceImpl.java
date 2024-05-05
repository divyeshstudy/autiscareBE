package com.api.autiscare.service;

import com.api.autiscare.entity.Child;
import com.api.autiscare.entity.TherapySession;
import com.api.autiscare.exception.DataConstraintVoilationException;
import com.api.autiscare.exception.DuplicateRecordsException;
import com.api.autiscare.exception.NotFoundException;
import com.api.autiscare.exception.UnknownException;
import com.api.autiscare.repository.ChildReporsitory;
import com.api.autiscare.repository.TherapySessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author divyesh.dwivedi
 */
@Service
public class TheraphySessionServiceImpl implements TheraphySessionService {

    private static final Logger logger = LoggerFactory.getLogger(TheraphySessionServiceImpl.class);

    @Autowired
    private TherapySessionRepository therapySessionRepository;

    @Override
    public TherapySession save(TherapySession therapySession) {
        logger.info("theraphy session save method");
        logger.info("Incoming theraphy session = " + therapySession);
        try {
            TherapySession savedtherapySessionRecord = therapySessionRepository.save(therapySession);
            logger.info("theraphy session = " + savedtherapySessionRecord);
            return savedtherapySessionRecord;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}