package com.mercadolivro.controller

import com.mercadolivro.dto.request.PostBookRequest
import com.mercadolivro.dto.request.PutBookRequest
import com.mercadolivro.dto.response.BookResponse
import com.mercadolivro.dto.response.PageResponse
import com.mercadolivro.extension.toBookModel
import com.mercadolivro.extension.toPageResponse
import com.mercadolivro.extension.toResponse
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("books")
class BookController(
    val customerService: CustomerService,
    val bookService: BookService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid postBookRequest: PostBookRequest){
        val customer = customerService.getCustomerById(postBookRequest.customerId)
        bookService.create(postBookRequest.toBookModel(customer))
    }

    @GetMapping
    fun findAll(@PageableDefault(page = 0, size = 10) pageable: Pageable): PageResponse<BookResponse> =
        bookService.findAll(pageable).map { it.toResponse() }.toPageResponse()


    @GetMapping("/active")
    fun findActives(@PageableDefault(page = 0, size = 10) pageable: Pageable): Page<BookResponse> =
        bookService.findActives(pageable).map { it.toResponse() }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): BookResponse{
        return bookService.findById(id).toResponse()
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Int){
        return bookService.deleteById(id)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int,@RequestBody putBookRequest: PutBookRequest){
        val bookSaved = bookService.findById(id)
        bookService.update(putBookRequest.toBookModel(bookSaved))
    }
}