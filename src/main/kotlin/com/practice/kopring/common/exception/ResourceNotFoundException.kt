package com.practice.kopring.common.exception

import com.practice.kopring.common.enums.ErrorCode

class ResourceNotFoundException(errorCode: ErrorCode) : BusinessException(errorCode)