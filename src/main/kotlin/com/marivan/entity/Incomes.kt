package com.marivan.entity

import javax.persistence.*

@Entity
class Incomes (

        @Id @GeneratedValue
        var id : Long? = null,
        @ManyToOne(cascade = [CascadeType.ALL])
        var income_source_id: IncomeSources
)
