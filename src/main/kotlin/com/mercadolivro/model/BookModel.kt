package com.mercadolivro.model

import com.mercadolivro.enum.BookStatus
import com.mercadolivro.enum.Erros
import com.mercadolivro.exception.BadRequestException
import jakarta.persistence.*
import java.math.BigDecimal

@Entity(name="book")
data class BookModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var price:BigDecimal,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel? = null
){
    @Column
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null
        set(value) {
            if(field == BookStatus.CANCELADO || field == BookStatus.DELETADO)
                throw BadRequestException(Erros.ML102.message.format(field), Erros.ML102.code)
            field = value
        }

    constructor(id: Int? = null,
                name: String,
                price: BigDecimal,
                customer: CustomerModel? = null,
                status: BookStatus?): this(id,name,price,customer){
                    this.status = status
                }
}