package com.spring.module.auth.infrastructure.adapter.input.rest.exception

import com.module.prj.core.common.ResponseMessages

class MissingUserIdException(message: String = ResponseMessages.MISSING_USER_ID_ERROR) : RuntimeException(message)