// ApiSetServiceImpl.java
package com.apisettracker.service;

import com.apisettracker.dto.ApiSetBackendContextDTO;
import com.apisettracker.dto.ApiSetDetailDTO;
import com.apisettracker.dto.PageResponseDTO;
import com.apisettracker.exception.ResourceNotFoundException;
import com.apisettracker.model.ApiSetBackendContext;
import com.apisettracker.model.ApiSetDetail;
import com.apisettracker.repository.ApiSetDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiSetServiceImpl implements ApiSetService {

    private final ApiSetDetailRepository apiSetDetailRepository;

    @Autowired
    public ApiSetServiceImpl(ApiSetDetailRepository apiSetDetailRepository) {
        this.apiSetDetailRepository = apiSetDetailRepository;
    }

    @Override
    public List<ApiSetDetailDTO> getAllApiSets() {
        List<ApiSetDetail> apiSets = apiSetDetailRepository.findAll();
        return apiSets.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PageResponseDTO<ApiSetDetailDTO> getApiSetsPaginated(Pageable pageable) {
        Page<ApiSetDetail> page = apiSetDetailRepository.findAll(pageable);
        
        List<ApiSetDetailDTO> content = page.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        return new PageResponseDTO<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    public ApiSetDetailDTO getApiSetById(Long id) {
        ApiSetDetail apiSetDetail = apiSetDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("API Set not found with id: " + id));
        
        return convertToDTO(apiSetDetail);
    }

    @Override
    @Transactional
    public ApiSetDetailDTO createApiSet(ApiSetDetailDTO apiSetDetailDTO) {
        ApiSetDetail apiSetDetail = convertToEntity(apiSetDetailDTO);
        ApiSetDetail savedApiSetDetail = apiSetDetailRepository.save(apiSetDetail);
        return convertToDTO(savedApiSetDetail);
    }

    @Override
    @Transactional
    public ApiSetDetailDTO updateApiSet(Long id, ApiSetDetailDTO apiSetDetailDTO) {
        ApiSetDetail existingApiSetDetail = apiSetDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("API Set not found with id: " + id));
        
        // Update basic fields
        existingApiSetDetail.setApiSetName(apiSetDetailDTO.getApiSetName());
        existingApiSetDetail.setVersionName(apiSetDetailDTO.getVersionName());
        existingApiSetDetail.setOwnerName(apiSetDetailDTO.getOwnerName());
        
        // Clear existing backend contexts and add new ones
        existingApiSetDetail.getBackendContexts().clear();
        
        if (apiSetDetailDTO.getBackendContexts() != null) {
            for (ApiSetBackendContextDTO contextDTO : apiSetDetailDTO.getBackendContexts()) {
                ApiSetBackendContext context = new ApiSetBackendContext();
                context.setBackendContext(contextDTO.getBackendContext());
                context.setStatus(contextDTO.getStatus());
                existingApiSetDetail.addBackendContext(context);
            }
        }
        
        ApiSetDetail updatedApiSetDetail = apiSetDetailRepository.save(existingApiSetDetail);
        return convertToDTO(updatedApiSetDetail);
    }

    @Override
    @Transactional
    public void deleteApiSet(Long id) {
        if (!apiSetDetailRepository.existsById(id)) {
            throw new ResourceNotFoundException("API Set not found with id: " + id);
        }
        apiSetDetailRepository.deleteById(id);
    }

    private ApiSetDetailDTO convertToDTO(ApiSetDetail apiSetDetail) {
        ApiSetDetailDTO dto = new ApiSetDetailDTO();
        dto.setId(apiSetDetail.getId());
        dto.setApiSetName(apiSetDetail.getApiSetName());
        dto.setVersionName(apiSetDetail.getVersionName());
        dto.setOwnerName(apiSetDetail.getOwnerName());
        dto.setAddedAt(apiSetDetail.getAddedAt());
        dto.setAddedBy(apiSetDetail.getAddedBy());
        dto.setModifiedAt(apiSetDetail.getModifiedAt());
        dto.setModifiedBy(apiSetDetail.getModifiedBy());
        
        List<ApiSetBackendContextDTO> contextDTOs = apiSetDetail.getBackendContexts().stream()
                .map(this::convertContextToDTO)
                .collect(Collectors.toList());
        
        dto.setBackendContexts(contextDTOs);
        return dto;
    }

    private ApiSetBackendContextDTO convertContextToDTO(ApiSetBackendContext context) {
        ApiSetBackendContextDTO dto = new ApiSetBackendContextDTO();
        dto.setId(context.getId());
        dto.setBackendContext(context.getBackendContext());
        dto.setStatus(context.getStatus());
        dto.setAddedAt(context.getAddedAt());
        dto.setAddedBy(context.getAddedBy());
        dto.setModifiedAt(context.getModifiedAt());
        dto.setModifiedBy(context.getModifiedBy());
        return dto;
    }

    private ApiSetDetail convertToEntity(ApiSetDetailDTO dto) {
        ApiSetDetail entity = new ApiSetDetail();
        entity.setApiSetName(dto.getApiSetName());
        entity.setVersionName(dto.getVersionName());
        entity.setOwnerName(dto.getOwnerName());
        
        if (dto.getBackendContexts() != null) {
            for (ApiSetBackendContextDTO contextDTO : dto.getBackendContexts()) {
                ApiSetBackendContext context = new ApiSetBackendContext();
                context.setBackendContext(contextDTO.getBackendContext());
                context.setStatus(contextDTO.getStatus());
                entity.addBackendContext(context);
            }
        }
        
        return entity;
    }
}