package com.mercadolivro.controller.mapper

import com.mercadolivro.dto.request.PostPurchaseRequest
import com.mercadolivro.model.PurchaseModel
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.stereotype.Component

@Component
class PurchaseMapper(
    private val bookService: BookService,
    private val customerService: CustomerService
    ) {

    fun toModel(request:PostPurchaseRequest): PurchaseModel{
        val customer = customerService.getCustomerById(request.customerId)
        val book = bookService.findByIds(request.bookIds)

        return PurchaseModel(
            customer = customer,
            books = book,
            price = book.sumOf { it.price }
        )
    }

}