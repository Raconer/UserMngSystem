package com.spring.module.auth.infrastructure.adapter.input.rest.exception

import com.spring.module.auth.infrastructure.rest.constant.ResponseMessages

class UserNotFoundException(message: String = ResponseMessages.USER_NOT_FOUND, ) : RuntimeException(message)