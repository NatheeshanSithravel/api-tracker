// ApiSetDetail.java
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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "API_SET_DETAIL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ApiSetDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "API_SET_NAME", length = 100, nullable = false)
    private String apiSetName;

    @Column(name = "VERSION_NAME", length = 10, nullable = false)
    private String versionName;

    @Column(name = "OWNER_NAME", length = 100, nullable = false)
    private String ownerName;

    @CreatedDate
    @Column(name = "ADDED_AT", updatable = false)
    private LocalDateTime addedAt;

    @CreatedBy
    @Column(name = "ADDED_BY", length = 100, nullable = false)
    private String addedBy;

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @LastModifiedBy
    @Column(name = "MODIFIED_BY", length = 100)
    private String modifiedBy;

    @OneToMany(mappedBy = "apiSetDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApiSetBackendContext> backendContexts = new ArrayList<>();

    public void addBackendContext(ApiSetBackendContext context) {
        backendContexts.add(context);
        context.setApiSetDetail(this);
    }

    public void removeBackendContext(ApiSetBackendContext context) {
        backendContexts.remove(context);
        context.setApiSetDetail(null);
    }
}

