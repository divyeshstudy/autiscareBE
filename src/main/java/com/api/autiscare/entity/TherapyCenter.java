package com.api.autiscare.entity;

/**
 * @author divyesh.dwivedi
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class TherapyCenter {

   /* @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )*/

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id")
    @GenericGenerator(
            name = "sequence_id",
            strategy = "com.api.autiscare.utility.idgenerator.TheraphyCenterSequenceIdGenerator"
    )
    @Column(name = "centerid",nullable = false)
    String centerId;

    @Column(name = "centername", nullable = false)
    String centerName;

    @Column(name = "theraphytype", nullable = false)
    String theraphyType;

    @Column(nullable = false)
    String address1;

    String address2;

    @Column(nullable = false)
    String city;

    @Column(nullable = false)
    String state;

    @Column(nullable = false)
    String pin;

    @Column(name = "centernumber",nullable = false)
    String centerNumber;

    @Column(name = "centeremail",nullable = false)
    String centerEmail;

    @Column(name = "ownername",nullable = false)
    String ownerName;

    @Column(name = "ownernumber",nullable = false)
    String ownerNumber;

    @Column(name = "owneremail",nullable = false)
    String ownerEmail;

    @Column(nullable = false)
    String gstno;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userid_fk")
    private User user;
}