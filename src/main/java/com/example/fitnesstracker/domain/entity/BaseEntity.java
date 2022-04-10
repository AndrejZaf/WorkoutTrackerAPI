package com.example.fitnesstracker.domain.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @NotNull
    @Column(columnDefinition = "VARCHAR(255)")
    @Type(type = "uuid-char")
    private UUID uid = UUID.randomUUID();

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime deletedOn;
}
