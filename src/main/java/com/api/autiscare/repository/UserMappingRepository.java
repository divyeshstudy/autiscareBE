package com.api.autiscare.repository;

/**
 * @author divyesh.dwivedi
 */

import com.api.autiscare.entity.UserMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMappingRepository extends JpaRepository<UserMapping, String> {
    @Query("select um from UserMapping um where um.userId = ?1")
    List<UserMapping> findAllByUserId(String userId);

    List<UserMapping> findAllByChildId(String id);

    List<UserMapping> findByUserId(String userId);

    List<UserMapping> findAllByCentreId(String id);

    List<UserMapping> findAllByTherapistId(String id);
}
