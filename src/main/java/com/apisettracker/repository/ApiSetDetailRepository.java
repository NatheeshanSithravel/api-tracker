// ApiSetDetailRepository.java
package com.apisettracker.repository;

import com.apisettracker.model.ApiSetDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiSetDetailRepository extends JpaRepository<ApiSetDetail, Long> {
    
}