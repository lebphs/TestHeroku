package com.marivan.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class IncomeSources(
        @Id
        @GeneratedValue
        var id: Long? = null,
        var name: String
)
