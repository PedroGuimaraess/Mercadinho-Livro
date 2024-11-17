package com.mercadolivro.dto.request

import jakarta.validation.constraints.NotEmpty

data class PutCustomerRequest(
    var name: String,

    var email:String
)
