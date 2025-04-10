package com.musinsa.subject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreatedDate
    public Instant createdAt;

    @Column(name = "created_by", updatable = false, nullable = false)
    @CreatedBy
    public String createdBy;

    @Column(name = "modified_at", nullable = false)
    @LastModifiedDate
    public Instant modifiedAt;

    @Column(name = "modified_by", nullable = false)
    @LastModifiedBy
    public String modifiedBy;

}