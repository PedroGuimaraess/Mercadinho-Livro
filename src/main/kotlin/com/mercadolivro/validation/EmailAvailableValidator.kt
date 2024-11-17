package com.mercadolivro.validation

import com.mercadolivro.service.CustomerService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class EmailAvailableValidator(var customerSerice: CustomerService): ConstraintValidator<EmailAvailable, String>{
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if(value.isNullOrEmpty()){
            return false
        }
        return customerSerice.emailAvailable(value)
    }

}
