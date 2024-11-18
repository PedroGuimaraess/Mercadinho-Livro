package com.mercadolivro.dto.request

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class PostPurchaseRequest(
    @field:NotNull
    @field:Positive
    val customerId: Int,

    @NotNull
    val bookIds: Set<Int>
)
