package com.practice.kopring.common.exception

import com.practice.kopring.common.enums.ErrorCode

open class BusinessException(val errorCode: ErrorCode) : RuntimeException(errorCode.message)