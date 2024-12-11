package com.mercadolivro.controller

import com.mercadolivro.dto.response.CustomerResponse
import com.mercadolivro.extension.primeiraLetra
import com.mercadolivro.extension.toResponse
import com.mercadolivro.service.CustomerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("admin")
class AdminController(
    val customerService: CustomerService
) {

    @GetMapping("/report")
    fun getAllCustomer(): String{
        return "This is a Report. Only Admin can see it!"
    }

    @GetMapping("all")
    fun getAllCustomer(@RequestParam name: String?): List<CustomerResponse>{
        "teste".primeiraLetra()
        return customerService.getAllCustomer(name).map {
            it.toResponse()
        }
    }
}