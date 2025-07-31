package com.spring.module.auth.infrastructure.adapter.input.rest.exception

import com.module.prj.core.util.ResponseMessages

class UserNotFoundException(message: String = ResponseMessages.USER_NOT_FOUND, ) : RuntimeException(message)