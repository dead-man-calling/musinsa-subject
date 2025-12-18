package com.musinsa.subject.entity

import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable

@Entity
@Table(name = "category")
@DynamicInsert
@DynamicUpdate
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    var categoryId: Long? = null,

    @Column(name = "category_name", nullable = false, length = 100)
    var categoryName: String
) : BaseTimeEntity(), Serializable
