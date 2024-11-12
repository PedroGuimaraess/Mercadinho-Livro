package com.mercadolivro.extension

import com.mercadolivro.dto.request.PostBookRequest
import com.mercadolivro.dto.request.PostCustomerRequest
import com.mercadolivro.dto.request.PutBookRequest
import com.mercadolivro.dto.request.PutCustomerRequest
import com.mercadolivro.dto.response.BookResponse
import com.mercadolivro.dto.response.CustomerResponse
import com.mercadolivro.enum.BookStatus
import com.mercadolivro.enum.CostumerStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel

fun PostCustomerRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(name = this.name, email = this.email, status = CostumerStatus.ATIVO)
}

fun String.primeiraLetra():Char{
    return this.get(0)
}

fun PutCustomerRequest.toCustomerModel(previousCustomer:CustomerModel): CustomerModel {
    return CustomerModel(id = previousCustomer.id,
        name = this.name,
        email = this.email,
        status = previousCustomer.status)
}

fun PostBookRequest.toBookModel(customerModel: CustomerModel): BookModel {
    return BookModel(name = this.name,
        price = this.price,
        status = BookStatus.ATIVO,
        customer = customerModel)
}

fun PutBookRequest.toBookModel(previousBook: BookModel): BookModel {
    return BookModel(
        id = previousBook.id,
        name = this.name ?: previousBook.name,
        price = this.price ?: previousBook.price,
        status = previousBook.status,
        customer = previousBook.customer
    )
}

fun CustomerModel.toResponse(): CustomerResponse {
    return CustomerResponse(
        id = this.id,
        name = this.name,
        email =  this.name,
        status = this.status
    )
}

fun BookModel.toResponse(): BookResponse {
    return BookResponse(
        id = this.id,
        name = this.name,
        price = this.price,
        status = this.status,
        customer = this.customer
    )
}