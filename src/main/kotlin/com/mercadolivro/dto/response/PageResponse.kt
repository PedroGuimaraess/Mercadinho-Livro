package com.mercadolivro.dto.response

data class PageResponse<T>(
    var items: List<T>,
    var currentPages: Int,
    var totalItems: Long,
    var totalPages: Int,

)
