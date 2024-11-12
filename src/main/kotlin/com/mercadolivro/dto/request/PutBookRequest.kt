package com.mercadolivro.dto.request

import java.math.BigDecimal

data class PutBookRequest (
    var name: String?,
    var price: BigDecimal?
)


