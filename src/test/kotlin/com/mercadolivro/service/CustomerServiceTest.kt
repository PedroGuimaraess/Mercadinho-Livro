package com.mercadolivro.service

import com.mercadolivro.enum.CostumerStatus
import com.mercadolivro.enum.Erros
import com.mercadolivro.enum.Role
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.helper.buildCustomer
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(MockKExtension::class)
class CustomerServiceTest{

    @MockK
    private lateinit var customerRepository: CustomerRepository
    @MockK
    private lateinit var bookService: BookService

    @MockK
    private lateinit var bCrypt: BCryptPasswordEncoder

    @InjectMockKs
    @SpyK 
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
    fun `should throw error when customer not found find by id`(){
        val id = Random().nextInt()

        every { customerRepository.findById(id) } returns Optional.empty()

        val error = assertThrows<NotFoundException> { customerService.getCustomerById(id) }

        assertEquals("Customer [${id}] not exists", error.message)
        assertEquals("ML-201", error.errorCode)
        verify (exactly = 1 ) {customerRepository.findById(id)}
    }

    @Test
    fun `should update customer`(){
        val id = Random().nextInt()
        val fakeCustomer = buildCustomer(id = id)

        every { customerRepository.existsById(id) } returns true
        every { customerRepository.save(fakeCustomer) } returns fakeCustomer

        customerService.updateCustomer(fakeCustomer)
        verify (exactly = 1 ) {customerRepository.existsById(id)}
        verify (exactly = 1 ) {customerRepository.save(fakeCustomer)}
    }

    @Test
    fun `should throw error when customer id dont exists UpdadeFunction`(){
        val id = Random().nextInt()
        val fakeCustomer = buildCustomer(id = id)

        every { !customerRepository.existsById(id) } returns false
        every { customerRepository.save(fakeCustomer) } returns fakeCustomer

        val error = assertThrows<NotFoundException> { customerService.updateCustomer(fakeCustomer) }
        assertEquals("Customer [${id}] not exists", error.message)
        assertEquals("ML-201", error.errorCode)

        verify (exactly = 1 ) {customerRepository.existsById(id)}
        verify (exactly = 0 ) {customerRepository.save(any())}
    }

    @Test
    fun `should delete customer by id`(){
        val id =  Random().nextInt()
        val fakeCustomer = buildCustomer(id = id)
        val expectedCustomer = fakeCustomer.copy(status = CostumerStatus.INATIVO)

        every { customerService.getCustomerById(id) } returns fakeCustomer
        every { bookService.deleteByCustomer(fakeCustomer) } just runs
        every { customerRepository.save(expectedCustomer) } returns expectedCustomer

        customerService.deleteCustomer(id)

        verify (exactly = 0 ) {bookService.findById(id)}
        verify (exactly = 1 ) {bookService.deleteByCustomer(fakeCustomer)}
        verify (exactly = 1 ) {customerRepository.save(expectedCustomer)}
    }

    @Test
    fun `should throw not found when delete customer`(){
        val id =  Random().nextInt()
        val fakeCustomer = buildCustomer(id = id)

        every { customerService.getCustomerById(id) } throws NotFoundException(Erros.ML201.message.format(id), Erros.ML201.code)

        val error = assertThrows<NotFoundException> { customerService.deleteCustomer(id) }
        assertEquals("Customer [${id}] not exists", error.message)
        assertEquals("ML-201", error.errorCode)

        verify (exactly = 0 ) {bookService.findById(id)}
        verify (exactly = 0 ) {bookService.deleteByCustomer(any())}
        verify (exactly = 0 ) {customerRepository.save(any())}
    }

    @Test
    fun `should response true when email is Available`(){
        val email =  "${UUID.randomUUID()}@email.com"

        every { customerRepository.existsByEmail(email) } returns false

        val emailAvailable = customerService.emailAvailable(email)

        assertTrue(emailAvailable)
        verify (exactly = 1 ) {customerRepository.existsByEmail(email)}
    }

    @Test
    fun `should response false when email is Unavailable`(){
        val email =  "${UUID.randomUUID()}@email.com"

        every { customerRepository.existsByEmail(email) } returns true

        val emailAvailable = customerService.emailAvailable(email)

        assertFalse(emailAvailable)
        verify (exactly = 1 ) {customerRepository.existsByEmail(email)}
    }


    @Test
    fun `fake test`(){
        val resultado = soma(2,3)
        assertEquals(5,resultado)
    }

    fun soma(a:Int, b:Int):Int{
        return a+ b
    }
}