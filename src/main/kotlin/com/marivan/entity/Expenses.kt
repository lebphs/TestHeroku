package com.marivan.entity

import java.util.*
import javax.persistence.*

@Entity
class Expenses(
        @Id
        @GeneratedValue
        var id: Long? = null,
        var amount: String,
        var date: Date? = Date(),
        @ManyToOne(cascade = [CascadeType.ALL])
        var category_id: Categories
)
