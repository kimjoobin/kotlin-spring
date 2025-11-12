package com.practice.kopring.common.exception

open class BusinessException(val errorCode: ErrorCode) : RuntimeException(errorCode.message)