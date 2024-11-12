package com.mercadolivro.model

import com.mercadolivro.enum.CostumerStatus
import jakarta.persistence.*

@Entity(name="customer")
data class CustomerModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var email:String,

    @Column
    @Enumerated(EnumType.STRING)
    var status: CostumerStatus
)