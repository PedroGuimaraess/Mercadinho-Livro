package com.mercadolivro.dto.request

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class PostPurchaseRequest(
    @field:NotNull
    @field:Positive
    @JsonAlias("customer_id")
    val customerId: Int,

    @NotNull
    @JsonAlias("book_ids")
    val bookIds: Set<Int>
)
