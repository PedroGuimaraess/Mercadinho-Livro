package com.mercadolivro.model

import com.mercadolivro.enum.CostumerStatus
import com.mercadolivro.enum.Profile
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
    var status: CostumerStatus,

    @Column
    val password: String,

    @Column(name = "role")
    @ElementCollection(targetClass = Profile::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "customer_roles", joinColumns = [JoinColumn(name = "customer_id")])
    @Enumerated(EnumType.STRING)
    var roles: Set<Profile> = setOf()
)