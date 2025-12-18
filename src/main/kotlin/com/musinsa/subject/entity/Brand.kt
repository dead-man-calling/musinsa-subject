package com.musinsa.subject.entity

import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable

@Entity
@Table(name = "brand")
@DynamicInsert
@DynamicUpdate
class Brand(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    var brandId: Long? = null,

    @Column(name = "brand_name", nullable = false, length = 100)
    var brandName: String
) : BaseTimeEntity(), Serializable
