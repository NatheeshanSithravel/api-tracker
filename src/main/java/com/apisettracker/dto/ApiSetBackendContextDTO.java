// ApiSetBackendContextDTO.java
package com.apisettracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiSetBackendContextDTO {
    
    private Long id;
    
    @NotBlank(message = "Backend context is required")
    @Size(max = 200, message = "Backend context cannot exceed 200 characters")
    private String backendContext;
    
    @NotBlank(message = "Status is required")
    @Size(max = 10, message = "Status cannot exceed 10 characters")
    private String status;
    
    private LocalDateTime addedAt;
    private String addedBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
}
