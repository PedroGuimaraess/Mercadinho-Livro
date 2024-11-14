package com.mercadolivro.dto.response

data class ErrorResponse(
    var httpCode: Int,
    var message: String,
    var internalCode: String,
    var erros: List<FieldErrorResponse>?
)
