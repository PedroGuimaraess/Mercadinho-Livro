package com.mercadolivro.service

import com.mercadolivro.enum.CostumerStatus
import com.mercadolivro.enum.Erros
import com.mercadolivro.enum.Role
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody


@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val bookService: BookService,
    private val bCrypt: BCryptPasswordEncoder
)
{
    fun getAllCustomer(name: String?): List<CustomerModel>{
        name?.let{
            return customerRepository.findByNameContaining(it)
        }
        return customerRepository.findAll().toList()
    }

    fun getCustomerById(@PathVariable id:Int): CustomerModel{
        return customerRepository.findById(id).orElseThrow{NotFoundException(Erros.ML201.message.format(id), Erros.ML201.code)}
        //return customerRepository.findById(id).get()
    }

    fun createCustomer(@RequestBody postCustomerRequest: CustomerModel){
        val customerCopy = postCustomerRequest.copy(
            roles = setOf(Role.CUSTOMER),
            password = bCrypt.encode(postCustomerRequest.password)
        )
        customerRepository.save(customerCopy)
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

    fun emailAvailable(email: String): Boolean {
       return !customerRepository.existsByEmail(email)
    }
}
