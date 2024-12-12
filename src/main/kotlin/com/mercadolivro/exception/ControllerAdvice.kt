package com.mercadolivro.exception

import com.mercadolivro.dto.response.ErrorResponse
import com.mercadolivro.dto.response.FieldErrorResponse
import com.mercadolivro.enum.Erros
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.message,
            ex.errorCode,
            null
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.message,
            ex.errorCode,
            null
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handlenMethodArgumentNotValidException(ex: MethodArgumentNotValidException, request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            Erros.ML001.message,
            Erros.ML001.code,
            ex.bindingResult.fieldErrors.map{
                FieldErrorResponse(
                    it.defaultMessage ?: "invalid", it.field)
            }
        )
        return ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedExceptionex(ex: AccessDeniedException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            HttpStatus.FORBIDDEN.value(),
            Erros.ML000.message,
            Erros.ML000.code,
            null
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }
}