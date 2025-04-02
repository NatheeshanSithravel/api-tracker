package com.apisettracker.controller;

import com.apisettracker.dto.ApiSetDetailDTO;
import com.apisettracker.dto.PageResponseDTO;
import com.apisettracker.service.ApiSetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apisets")
public class ApiSetController {

    private final ApiSetService apiSetService;

    @Autowired
    public ApiSetController(ApiSetService apiSetService) {
        this.apiSetService = apiSetService;
    }

    @GetMapping
    public ResponseEntity<List<ApiSetDetailDTO>> getAllApiSets() {
        List<ApiSetDetailDTO> apiSets = apiSetService.getAllApiSets();
        return ResponseEntity.ok(apiSets);
    }

    @GetMapping("/paged")
    public ResponseEntity<PageResponseDTO<ApiSetDetailDTO>> getApiSetsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        PageResponseDTO<ApiSetDetailDTO> response = apiSetService.getApiSetsPaginated(pageable);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSetDetailDTO> getApiSetById(@PathVariable Long id) {
        ApiSetDetailDTO apiSet = apiSetService.getApiSetById(id);
        return ResponseEntity.ok(apiSet);
    }

    @PostMapping
    public ResponseEntity<ApiSetDetailDTO> createApiSet(@Valid @RequestBody ApiSetDetailDTO apiSetDetailDTO) {
        ApiSetDetailDTO createdApiSet = apiSetService.createApiSet(apiSetDetailDTO);
        return new ResponseEntity<>(createdApiSet, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiSetDetailDTO> updateApiSet(
            @PathVariable Long id, 
            @Valid @RequestBody ApiSetDetailDTO apiSetDetailDTO) {
        ApiSetDetailDTO updatedApiSet = apiSetService.updateApiSet(id, apiSetDetailDTO);
        return ResponseEntity.ok(updatedApiSet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApiSet(@PathVariable Long id) {
        apiSetService.deleteApiSet(id);
        return ResponseEntity.noContent().build();
    }
}