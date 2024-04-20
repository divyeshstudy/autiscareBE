package com.api.autiscare.repository;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChildReporsitory extends JpaRepository<Child, String> {
    List<Child> findAllByFirstNameAndLastName(String firstName, String lastName);

    List<Child> findAllByMotherMobile(String motherMobile);

    List<Child> findAllByFatherMobile(String fatherMobile);

    List<Child> findAllByFirstNameAndLastNameAndMotherMobileAndFatherMobile(String firstName, String lastName, String motherMobile, String fatherMobile);

    Optional<Child> findByUser(String userId);
}
