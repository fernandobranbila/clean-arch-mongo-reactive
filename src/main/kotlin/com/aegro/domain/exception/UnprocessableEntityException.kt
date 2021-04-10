package com.aegro.domain.exception

import com.aegro.domain.exception.GenericCodeException

class UnprocessableEntityException(message: String? = null, code: String = "") : GenericCodeException(message, code) {
}
