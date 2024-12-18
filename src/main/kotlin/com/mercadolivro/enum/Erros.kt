package com.mercadolivro.enum

enum class Erros(val code: String, val message: String) {

    ML000("ML-000", "Unauthorized"),
    ML001("ML-001", "Invalid request"),
    ML002("ML-002", "Bad Request"),
    ML101("ML-101", "Book [%s] not exists"),
    ML102("ML-102","Cannot update book with status[%s]"),
    ML201("ML-201", "Customer [%s] not exists")


}