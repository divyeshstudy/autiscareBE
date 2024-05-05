package com.api.autiscare.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author divyesh.dwivedi
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TherapySession {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id")
    @GenericGenerator(
            name = "sequence_id",
            strategy = "com.api.autiscare.utility.idgenerator.TherapySessionSequenceIdGenerator"
    )
    @Column(name = "id")
    String theraphySessionId;

    @Column(name = "childId", nullable = false)
    String childId;

    @Column(name = "therapistId", nullable = false)
    String therapistId;

    @Column(name = "centerId", nullable = false)
    String centerId;

    @Column(name = "therapyType", nullable = false)
    String therapyType;

    @Column(name = "therapyFee", nullable = false)
    String therapyFee;

    @Column(name = "therapyStartDate", nullable = false)
    Date therapyStartDate;

    @Column(name = "sessionDays", nullable = false)
    Integer sessionDays;

    @Column(name = "monday", nullable = false)
    Boolean monday;

    @Column(name = "tuesday", nullable = false)
    Boolean tuesday;

    @Column(name = "wednesday", nullable = false)
    Boolean wednesday;

    @Column(name = "thursday", nullable = false)
    Boolean thursday;

    @Column(name = "friday", nullable = false)
    Boolean friday;

    @Column(name = "satuday", nullable = false)
    Boolean satuday;

    @Column(name = "sunday", nullable = false)
    Boolean sunday;

    @Column(name = "timeForm", nullable = false)
    Date timeForm;

    @Column(name = "timeTo", nullable = false)
    Date timeTo;

    @Column(name = "duration", nullable = false)
    String duration;

}
