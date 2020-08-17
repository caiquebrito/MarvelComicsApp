package com.marvelcomics.brito.data.repository

import com.marvelcomics.brito.data.handler.ResponseHandler

open class BaseRepositoryImpl {
    protected val responseHandler = ResponseHandler()
}