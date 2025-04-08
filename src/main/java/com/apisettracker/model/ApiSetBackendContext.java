// ApiSetBackendContext.java
package com.apisettracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "API_SET_BACKEND_CONTEXT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ApiSetBackendContext {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "API_SET_DETAIL_ID", nullable = false)
    private ApiSetDetail apiSetDetail;

    @Column(name = "BACKEND_CONTEXT", length = 200, nullable = false)
    private String backendContext;

    @Column(name = "STATUS", length = 10, nullable = false)
    private String status;

    @CreatedDate
    @Column(name = "ADDED_AT", nullable = false, updatable = false)
    private LocalDateTime addedAt;

    @CreatedBy
    @Column(name = "ADDED_BY", length = 100 )
    private String addedBy;

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @LastModifiedBy
    @Column(name = "MODIFIED_BY", length = 100)
    private String modifiedBy;
}