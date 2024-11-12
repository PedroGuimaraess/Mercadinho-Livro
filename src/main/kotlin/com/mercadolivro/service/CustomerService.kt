package com.mercadolivro.service

import com.mercadolivro.enum.CostumerStatus
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody


@Service
class CustomerService(
    val customerRepository: CustomerRepository,
    val bookService: BookService
)
{
    fun getAllCustomer(name: String?): List<CustomerModel>{
        name?.let{
            return customerRepository.findByNameContaining(it)
        }
        return customerRepository.findAll().toList()
    }

    fun getCustomerById(@PathVariable id:Int): CustomerModel{
        return customerRepository.findById(id).get()
        //return customerRepository.findById(id).orElseThrow()
    }

    fun createCustomer(@RequestBody postCustomerRequest: CustomerModel){
        customerRepository.save(postCustomerRequest)
    }

    fun updateCustomer(putCustomerRequest: CustomerModel){
        if(!customerRepository.existsById(putCustomerRequest.id!!)){
                throw Exception()
            }
        customerRepository.save(putCustomerRequest)
    }

    fun deleteCustomer(id: Int){
//        if(!customerRepository.existsById(id)){
//            throw Exception()
//        }
        val customer = getCustomerById(id)
        bookService.deleteByCustomer(customer)

        customer.status = CostumerStatus.INATIVO

        customerRepository.save(customer)
    }

}
