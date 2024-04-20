package com.api.autiscare.repository;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.Child;
import com.api.autiscare.entity.Therapist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TherapistRepository extends JpaRepository<Therapist, String> {

    List<Therapist> findAllByFirstNameAndLastNameAndPhoneAndEmail(String firstName, String lastName, String phone, String email);
}
