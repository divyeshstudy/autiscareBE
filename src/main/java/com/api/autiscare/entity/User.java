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
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id")
    @GenericGenerator(
            name = "sequence_id",
            strategy = "com.api.autiscare.utility.idgenerator.UserSequenceIdGenerator"
    )
    @Column(name = "id", nullable = false)
    String userId;

    @Column(name = "username", nullable = false)
    String username;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "firstname", nullable = false)
    String firstName;

    @Column(name = "lastname", nullable = false)
    String lastName;

    @Column(name = "mobilenumber", nullable = false)
    String mobileNumber;

    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "mobileverified", nullable = false)
    Boolean mobileVerified;

    @Column(name = "emailverified", nullable = false)
    Boolean emailVerified;

    @Column(name = "lastpassword1")
    String lastPassword1;

    @Column(name = "lastpassword2")
    String lastPassword2;

    @Column(name = "lastpassword3")
    String lastPassword3;

    @Column(name = "usertype", nullable = false)
    String userType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Child> child;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<TherapyCenter>  therapyCenter;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Therapist> therapist;
}