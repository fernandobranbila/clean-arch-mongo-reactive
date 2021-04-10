package com.aegro.domain.exception

class BadRequestException(message: String? = null, code: String = "") : GenericCodeException(message, code) {


}