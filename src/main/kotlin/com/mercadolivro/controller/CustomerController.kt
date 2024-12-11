package com.mercadolivro.controller

import com.mercadolivro.dto.request.PostCustomerRequest
import com.mercadolivro.dto.request.PutCustomerRequest
import com.mercadolivro.dto.response.CustomerResponse
import com.mercadolivro.extension.toCustomerModel
import com.mercadolivro.extension.toResponse
import com.mercadolivro.security.UserCanOnlyAcessTheirOwnResouce
import com.mercadolivro.service.CustomerService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customers")
class CustomerController(
    val customerService: CustomerService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@RequestBody @Valid postCustomerRequest: PostCustomerRequest){
        customerService.createCustomer(postCustomerRequest.toCustomerModel())

    }

    @GetMapping("/{id}")
    @UserCanOnlyAcessTheirOwnResouce
    fun getCustomer(@PathVariable id:Int): CustomerResponse{
        return customerService.getCustomerById(id).toResponse()
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @UserCanOnlyAcessTheirOwnResouce
    fun updateCustomer(@PathVariable id:Int,@RequestBody @Valid putCustomerRequest: PutCustomerRequest){
        val costumerSaved = customerService.getCustomerById(id)
        customerService.updateCustomer(putCustomerRequest.toCustomerModel(costumerSaved))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@PathVariable id:Int){
        customerService.deleteCustomer(id)
    }
}