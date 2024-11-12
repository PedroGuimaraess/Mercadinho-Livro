package com.mercadolivro.dto.request

import com.mercadolivro.model.CustomerModel

data class PostCustomerRequest(
    var name: String,
    var email:String
)