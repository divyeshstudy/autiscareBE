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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Therapist {

  /*  @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )*/
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id")
    @GenericGenerator(
          name = "sequence_id",
          strategy = "com.api.autiscare.utility.idgenerator.TheraphistSequenceIdGenerator"
    )
    String id;

    @Column(name = "firstName", nullable = false)
    String firstName;

    @Column(name = "lastName", nullable = false)
    String lastName;

    @Column(name = "age", nullable = false)
    Integer age;

    @Column(name = "gender", nullable = false)
    String gender;

    @Column(name = "phone", nullable = false)
    String phone;

  @Column(name = "email", nullable = false)
    String email;

  @Column(name = "address1", nullable = false)
    String address1;

  @Column(name = "address2", nullable = false)
    String address2;

  @Column(name = "city", nullable = false)
    String city;

  @Column(name = "state", nullable = false)
    String state;

  @Column(name = "pincode", nullable = false)
    Integer pincode;

  @Column(name = "degree", nullable = false)
    String degree;

  @Column(name = "collegename", nullable = false)
    String collegeName;

  @Column(name = "yearofpassing", nullable = false)
    Integer yearOfPassing;

  @Column(name = "degreeduration", nullable = false)
    Integer degreeDuration;

  @Column(name = "registrationnumber", nullable = false)
    String registrationNumber;

  @Column(name = "experience", nullable = false)
    Integer experience;

  @Column(name = "idprooftype", nullable = false)
    String idProofType;

  @Column(name = "idproofnumber", nullable = false)
    String idProofNumber;

  @Column(name = "pic", nullable = false)
    String pic;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "userid_fk")
  private User user;
}
