package com.lcy.base.core.data.net

class ApiException constructor(val errorCode: Int, errorMessage: String) : Exception(errorMessage)