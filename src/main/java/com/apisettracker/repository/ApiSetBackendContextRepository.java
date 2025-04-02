// ApiSetBackendContextRepository.java
package com.apisettracker.repository;

import com.apisettracker.model.ApiSetBackendContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiSetBackendContextRepository extends JpaRepository<ApiSetBackendContext, Long> {
}