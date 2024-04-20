package com.api.autiscare.repository;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.TherapyCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TherapistCenterRepository extends JpaRepository<TherapyCenter, String> {

    @Query("select count(*) from TherapyCenter tc where tc.centerName = ?1 and tc.theraphyType = ?2 and tc.centerNumber =?3 and tc.centerEmail =?4")
    public Integer getDuplicateUserBasedOnNameAndTypeAndNumberAndEmailRecords(String centerName, String centerType,String centerNumber, String centerEmail);

    @Query("select count(*) from TherapyCenter tc where tc.centerName = ?1 and tc.theraphyType = ?2 and tc.centerNumber =?3")
    public Integer getDuplicateUserBasedOnNameAndTypeAndNumberRecords(String centerName, String theraphyType, String centerNumber);

    @Query("select count(*) from TherapyCenter tc where tc.centerName = ?1 and tc.theraphyType = ?2 and tc.centerEmail =?3")
    public Integer getDuplicateUserBasedOnNameAndTypeAndEmailRecords(String centerName, String theraphyType, String centerEmail);

    @Query("select count(*) from TherapyCenter tc where tc.centerName = ?1 and tc.theraphyType = ?2")
    public Integer getDuplicateUserBasedOnNameAndTypeRecords(String centerName, String theraphyType);

    @Query("select count(*) from TherapyCenter tc where tc.centerName = ?1 and tc.centerNumber = ?2 and tc.centerEmail = ?3")
    public Integer getDuplicateUserBasedOnNameAndNumberAndEmailRecords(String centerName, String centerNumber, String centerEmail);

    @Query("select count(*) from TherapyCenter tc where tc.centerName = ?1 and tc.centerNumber = ?2")
    public Integer getDuplicateUserBasedOnNameAndNumberRecords(String centerName, String centerNumber);

    @Query("select count(*) from TherapyCenter tc where tc.centerName = ?1 and tc.centerEmail = ?2")
    public Integer getDuplicateUserBasedOnNameAndEmailRecords(String centerName, String centerEmail);

    @Query("select count(*) from TherapyCenter tc where tc.theraphyType = ?1 and tc.centerNumber = ?2 and tc.centerEmail = ?3")
    public Integer getDuplicateUserBasedOnTypeAndNumberAndEmailRecords(String theraphyType, String centerNumber, String centerEmail);

    @Query("select count(*) from TherapyCenter tc where tc.theraphyType = ?1 and tc.centerNumber = ?2")
    public Integer getDuplicateUserBasedOnTypeAndNumberRecords(String theraphyType, String centerNumber);

    @Query("select count(*) from TherapyCenter tc where tc.theraphyType = ?1 and tc.centerEmail = ?2")
    public Integer getDuplicateUserBasedOnTypeAndEmailRecords(String theraphyType, String centerEmail);

    @Query("select count(*) from TherapyCenter tc where tc.centerNumber = ?1 and tc.centerEmail = ?2")
    Integer getDuplicateUserBasedOnNumberAndEmailRecords(String centerNumber, String centerEmail);

    @Query("select count(*) from TherapyCenter tc where tc.centerName = ?1")
    public Integer getDuplicateUserBasedOnNameRecords(String centerName);

    @Query("select count(*) from TherapyCenter tc where tc.theraphyType = ?1")
    public Integer getDuplicateUserBasedOnTypeRecords(String theraphyType);

    @Query("select count(*) from TherapyCenter tc where tc.centerNumber = ?1")
    public Integer getDuplicateUserBasedOnNumberRecords(String centerNumber);

    @Query("select count(*) from TherapyCenter tc where tc.centerEmail = ?1")
    public Integer getDuplicateUserBasedOnEmailRecords(String centerEmail);
}
