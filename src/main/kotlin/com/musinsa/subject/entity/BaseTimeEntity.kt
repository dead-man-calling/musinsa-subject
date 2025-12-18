package com.musinsa.subject.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreatedDate
    var createdAt: Instant? = null

    @Column(name = "created_by", updatable = false, nullable = false)
    @CreatedBy
    var createdBy: String? = null

    @Column(name = "modified_at", nullable = false)
    @LastModifiedDate
    var modifiedAt: Instant? = null

    @Column(name = "modified_by", nullable = false)
    @LastModifiedBy
    var modifiedBy: String? = null
}
