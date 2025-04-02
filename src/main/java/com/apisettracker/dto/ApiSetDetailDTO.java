// ApiSetDetailDTO.java
package com.apisettracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiSetDetailDTO {
    
    private Long id;
    
    @NotBlank(message = "API set name is required")
    @Size(max = 100, message = "API set name cannot exceed 100 characters")
    private String apiSetName;
    
    @NotBlank(message = "Version name is required")
    @Size(max = 10, message = "Version name cannot exceed 10 characters")
    private String versionName;
    
    @NotBlank(message = "Owner name is required")
    @Size(max = 100, message = "Owner name cannot exceed 100 characters")
    private String ownerName;
    
    private LocalDateTime addedAt;
    private String addedBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    
    private List<ApiSetBackendContextDTO> backendContexts = new ArrayList<>();
}

