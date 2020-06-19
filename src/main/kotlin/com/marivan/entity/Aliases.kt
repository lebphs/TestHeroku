package com.marivan.entity

import javax.persistence.*

@Entity
class Aliases(
        @Id @GeneratedValue
        var id: Long? = null,
        var name: String,
        @ManyToOne(cascade = [CascadeType.ALL])
        var category_id: Categories
)
