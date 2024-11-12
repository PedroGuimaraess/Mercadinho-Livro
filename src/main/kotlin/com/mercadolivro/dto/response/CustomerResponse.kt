package com.mercadolivro.dto.response

import com.mercadolivro.enum.CostumerStatus

data class CustomerResponse (
    var id: Int? = null,

    var name: String,

    var email:String,

    var status: CostumerStatus
)
