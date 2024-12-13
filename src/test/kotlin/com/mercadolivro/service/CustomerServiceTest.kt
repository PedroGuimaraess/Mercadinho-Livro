package com.mercadolivro.service

import com.mercadolivro.enum.CostumerStatus
import com.mercadolivro.enum.Role
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class CustomerServiceTest{

    @MockK
    private lateinit var customerRepository: CustomerRepository
    @MockK
    private lateinit var bookService: BookService
    @MockK
    private lateinit var bCrypt: BCryptPasswordEncoder

    @InjectMockKs
    private lateinit var customerService: CustomerService

    @Test
    fun `should return all customers`(){
        val fakeCustomers = listOf(buildCustomer(), buildCustomer())

        every {  customerRepository.findAll() } returns fakeCustomers

        val customers = customerService.getAllCustomer(null)

        assertEquals(fakeCustomers, customers)
        verify(exactly = 1) {customerRepository.findAll()}
        verify(exactly = 0) {customerRepository.findByNameContaining(any())}
    }

    @Test
    fun `should return customers when name is informed`(){
        val name = UUID.randomUUID().toString()

        val fakeCustomers = listOf(buildCustomer(), buildCustomer())

        every {  customerRepository.findByNameContaining(name) } returns fakeCustomers

        val customers = customerService.getAllCustomer(name)

        assertEquals(fakeCustomers, customers)
        verify(exactly = 0) {customerRepository.findAll()}
        verify(exactly = 1) {customerRepository.findByNameContaining(name)}
    }

    @Test
    fun `should create customer and encrypt password`(){
        val initialPassword = Random().nextInt().toString()
        val fakeCustomer = buildCustomer(password = initialPassword)
        val fakePassword = UUID.randomUUID().toString()
        val fakeCustomerPasswordEncrypt = fakeCustomer.copy(password = fakePassword)

        every {  customerRepository.save(fakeCustomerPasswordEncrypt) } returns fakeCustomer
        every { bCrypt.encode(initialPassword) } returns fakePassword

        customerService.createCustomer(fakeCustomer)

        verify (exactly = 1 ) {customerRepository.save(fakeCustomerPasswordEncrypt)}
        verify (exactly = 1 ) {bCrypt.encode(initialPassword)}
    }

    @Test
    fun `should return customer by id`(){
        val id = Random().nextInt()
        val fakeCustomer = buildCustomer(id = id)

        every { customerRepository.findById(id) } returns Optional.of(fakeCustomer)

        val customer = customerService.getCustomerById(id)

        assertEquals(fakeCustomer, customer)
        verify (exactly = 1 ) {customerRepository.findById(id)}
    }

    @Test
    fun `should throw error when customer not found`(){
        val id = Random().nextInt()

        every { customerRepository.findById(id) } returns Optional.empty()

        val error = assertThrows<NotFoundException> { customerService.getCustomerById(id) }

        assertEquals("Customer [${id}] not exists", error.message)
        assertEquals("ML-201", error.errorCode)
        verify (exactly = 1 ) {customerRepository.findById(id)}
    }

    fun buildCustomer(
        id: Int? = null,
        name: String = "customer name",
        email:String = "${UUID.randomUUID()}@email.com",
        password: String = "password"
    ) = CustomerModel(
        id = id,
        name = name,
        email = email,
        status = CostumerStatus.ATIVO,
        password = password,
        roles = setOf(Role.CUSTOMER)
    )

    @Test
    fun `fake test`(){
        val resultado = soma(2,3)
        assertEquals(5,resultado)
    }

    fun soma(a:Int, b:Int):Int{
        return a+ b
    }
}