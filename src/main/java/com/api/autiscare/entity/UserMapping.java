package com.api.autiscare.entity;

/**
 * @author divyesh.dwivedi
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMapping {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id")
    @GenericGenerator(
            name = "sequence_id",
            strategy = "com.api.autiscare.utility.idgenerator.UserMappingSequenceIdGenerator"
    )
    String id;

    @Column(name ="userid")
    String userId;

    @Column(name ="userType")
    String userType;

    @Column(name ="centerid")
    String centreId;

    @Column(name ="therapistId")
    String therapistId;

    @Column(name ="childId")
    String childId;
}
