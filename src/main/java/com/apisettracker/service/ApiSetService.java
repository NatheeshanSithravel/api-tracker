// ApiSetService.java
package com.apisettracker.service;

import com.apisettracker.dto.ApiSetDetailDTO;
import com.apisettracker.dto.PageResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApiSetService {
    List<ApiSetDetailDTO> getAllApiSets();
    
    PageResponseDTO<ApiSetDetailDTO> getApiSetsPaginated(Pageable pageable);
    
    ApiSetDetailDTO getApiSetById(Long id);
    
    ApiSetDetailDTO createApiSet(ApiSetDetailDTO apiSetDetailDTO);
    
    ApiSetDetailDTO updateApiSet(Long id, ApiSetDetailDTO apiSetDetailDTO);
    
    void deleteApiSet(Long id);
}

