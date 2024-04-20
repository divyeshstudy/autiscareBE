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
public class Child {

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
           strategy = "com.api.autiscare.utility.idgenerator.ChildSequenceIdGenerator"
   )
    @Column(name = "id")
    String childId;

    @Column(name = "firstname", nullable = false)
    String firstName;

    @Column(name = "lastname", nullable = false)
    String lastName;

    @Column(name = "age", nullable = false)
    String age;

    @Column(name = "gender", nullable = false)
    String gender;

    @Column(name = "mothername", nullable = false)
    String motherName;

    @Column(name ="motherage", nullable = false)
    String motherAge;

    @Column(name = "mothermobile", nullable = false)
    String motherMobile;

    @Column(name = "motheremail", nullable = false)
    String motherEmail;

    @Column(name = "motheroccupation")
    String motherOccupation;

    @Column(name = "fathername", nullable = false)
    String fatherName;

    @Column(name = "fatherage" ,nullable = false)
    String fatherAge;

    @Column(name = "fathermobile", nullable = false)
    String fatherMobile;

    @Column(name = "fatheremail", nullable = false)
    String fatherEmail;

    @Column(name = "fatheroccupation")
    String fatherOccupation;

    @Column(name = "address1", nullable = false)
    String address1;

    @Column(name = "address2")
    String address2;

    @Column(name = "city", nullable = false)
    String city;

    @Column(name = "state", nullable = false)
    String state;

    @Column(name = "pincode", nullable = false)
    String pincode;

    @Column(name = "photo")
    String photo;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userid_fk")
    private User user;
}
