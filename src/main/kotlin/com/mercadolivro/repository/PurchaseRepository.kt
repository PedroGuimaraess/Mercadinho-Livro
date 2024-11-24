package com.mercadolivro.repository

import com.mercadolivro.model.PurchaseModel
import org.springframework.data.repository.CrudRepository
import javax.management.relation.InvalidRelationTypeException

interface PurchaseRepository: CrudRepository<PurchaseModel, Int>
