package com.aegro.application.config;

import com.aegro.domain.exception.BadRequestException
import com.aegro.domain.exception.GenericCodeException
import com.aegro.domain.exception.NotFoundException
import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

@ControllerAdvice
class GlobalExceptionHandler(
        private val messageSource: MessageSource
) {

    companion object {
        private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }

    @ExceptionHandler(value = [(Exception::class), (java.lang.Exception::class), (java.lang.IllegalStateException::class)])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    suspend fun exception(e: GenericCodeException): ResponseEntity<ErrorMessage> {
        log.error("Exception", e)
        return ResponseEntity(ErrorMessage(e.code!!, e.message!!), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(value = [(NotFoundException::class)])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    suspend fun notFoundException(e: GenericCodeException): ResponseEntity<ErrorMessage> {
        log.error("NotFoundException", e)
        return ResponseEntity(resolveExceptionMessage(e), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [(BadRequestException::class)])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    suspend fun badRequestException(e: GenericCodeException): ResponseEntity<ErrorMessage> {
        log.error("NotFoundException", e)
        return ResponseEntity(resolveExceptionMessage(e), HttpStatus.BAD_REQUEST)
    }

    private suspend fun resolveExceptionMessage(e: GenericCodeException) =
            if (e.code != null) {
                ErrorMessage(e.code, messageSource.getMessage(e.code, null, Locale("en-US")))
            } else {
                ErrorMessage("", "")
            }

}




