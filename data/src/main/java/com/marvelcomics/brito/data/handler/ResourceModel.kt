package com.marvelcomics.brito.data.handler

data class ResourceModel<out T>(val status: State, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): ResourceModel<T> {
            return ResourceModel(State.SUCCESS, data, null)
        }

        fun <T> error(statusCode: Int, message: String, data: T?): ResourceModel<T> {
            return ResourceModel(State.ERROR, data, message)
        }

        fun <T> loading(data: T?): ResourceModel<T> {
            return ResourceModel(State.LOADING, data, null)
        }
    }

    enum class State {
        LOADING, SUCCESS, ERROR, DEFAULT
    }
}