package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ExtraCourse;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ExtraCourse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtraCourseRepository extends JpaRepository<ExtraCourse,Long> {
    
}
