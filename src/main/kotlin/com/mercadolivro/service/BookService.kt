package com.mercadolivro.service

import com.mercadolivro.enum.BookStatus
import com.mercadolivro.enum.Erros
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {
    fun create(book: BookModel) {
        bookRepository.save(book)
    }

    fun findAll(pageable: Pageable): Page<BookModel> {
        return bookRepository.findAll(pageable)
    }

    fun findActives(pageable: Pageable): Page<BookModel> {
        return bookRepository.findByStatus(BookStatus.ATIVO, pageable)

    }

    fun findById(id: Int):BookModel {
        return bookRepository.findById(id).orElseThrow{NotFoundException(Erros.ML101.message.format(id), Erros.ML101.code)}
    }

    fun deleteById(id: Int) {
        val book = bookRepository.findById(id).orElseThrow()

        book.status = BookStatus.CANCELADO

        update(book)

    }

    fun update(book: BookModel) {
        bookRepository.save(book)
    }

    fun deleteByCustomer(customer: CustomerModel) {
       val books = bookRepository.findByCustomer(customer)
        for (book in books){
            book.status = BookStatus.DELETADO
        }
        bookRepository.saveAll(books)
    }

    fun findByIds(bookIds: Set<Int>): MutableList<BookModel> {
       return  bookRepository.findAllById(bookIds).toMutableList()
    }

    fun purchase(books: MutableList<BookModel>) {
        books.map {
            it.status = BookStatus.VENDIDO
        }
        bookRepository.saveAll(books)
    }
}
