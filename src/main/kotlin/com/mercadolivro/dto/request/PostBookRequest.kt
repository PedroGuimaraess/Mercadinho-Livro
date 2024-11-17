package com.mercadolivro.dto.request

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class PostBookRequest(
    @field:NotEmpty(message = "Nome deve ser informado")
    var name: String,

    @field:NotNull(message = "Price deve ser informado")
    var price: BigDecimal,

    @field:NotEmpty(message = "Customer deve ser informado")
    @JsonAlias("customer_id")
    var customerId:Int
)
