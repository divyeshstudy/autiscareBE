package com.api.autiscare.repository;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select count(*) from User u where u.username = ?1 and u.mobileNumber = ?2 and u.email =?3 and u.userType =?4")
    public Integer getDuplicateUserRecords(String username, String mobileNumber, String email, String userType);

    @Query("select count(*) from User u where u.mobileNumber = ?1")
    public Integer getDuplicateUserRecordsByPhoneNumber (String mobileNumber);

    @Query("select count(*) from User u where u.email = ?1")
    public Integer getDuplicateUserRecordsByEmail(String email);

    @Query("select count(*) from User u where u.username = ?1")
    public Integer getDuplicateUserRecordsByUsername(String username);

    public User findByUsername(String username);

    @Query("select count(*) from User u where u.username = ?1 and u.mobileNumber = ?2 and u.email =?3")
    public Integer getDuplicateUserRecordsByUserAndMobileNumberAndEmail(String username, String mobileNumber, String email);

    @Query("select count(*) from User u where u.username = ?1 and u.mobileNumber = ?2 and u.email =?3")
    public Integer getDuplicateUserRecordsByUsernameAndMobileNumberAndUserType(String username, String mobileNumber, String userType);

    @Query("select count(*) from User u where u.username = ?1 and u.mobileNumber = ?2")
    public Integer getDuplicateUserRecordsByUsernameAndMobileNumber(String username, String mobileNumber);

    @Query("select count(*) from User u where u.username = ?1 and u.email = ?2 and u.userType =?3")
    public Integer getDuplicateUserRecordsByUsernameAndEmailAndUserType(String username, String email, String userType);

    @Query("select count(*) from User u where u.username = ?1 and u.email = ?2")
    public Integer getDuplicateUserRecordsByUsernameAndEmail(String username, String email);

    @Query("select count(*) from User u where u.username = ?1 and u.userType = ?2")
    public Integer getDuplicateUserRecordsByUsernameAndUserType(String username, String userType);

    @Query("select count(*) from User u where u.mobileNumber = ?1 and u.email =?2 and u.userType = ?3")
    public Integer getDuplicateUserRecordsByMobileAndEmailAndUserType(String mobileNumber, String email, String userType);

    @Query("select count(*) from User u where u.mobileNumber = ?1 and u.email =?2")
    public Integer getDuplicateUserRecordsByMobileAndEmail(String mobileNumber, String email);

    @Query("select count(*) from User u where u.mobileNumber = ?1 and u.userType =?2")
    public Integer getDuplicateUserRecordsByMobileAndUserType(String mobileNumber, String userType);

    @Query("select count(*) from User u where u.email = ?1 and u.userType =?2")
    public Integer getDuplicateUserRecordsByEmailAndUserType(String email, String userType);

    @Query("select count(*) from User u where u.userType =?1")
    public Integer getDuplicateUserRecordsByUserType(String userType);
}
