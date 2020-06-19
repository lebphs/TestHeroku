package com.marivan.entity


import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id


@Entity
data class Categories (

    @Id @GeneratedValue
    var id : Long? = null,
    var name: String,
    var parent_id: Long? = null
)
