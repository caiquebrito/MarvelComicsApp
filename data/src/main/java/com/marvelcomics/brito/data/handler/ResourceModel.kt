package com.marvelcomics.brito.data.handler

data class ResourceModel<out Type>(val status: State, val data: Type?, val message: String?) {
    companion object {
        fun <Type> success(data: Type?): ResourceModel<Type> {
            return ResourceModel(State.SUCCESS, data, null)
        }

        fun <Type> error(statusCode: Int, message: String, data: Type?): ResourceModel<Type> {
            return ResourceModel(State.ERROR, data, message)
        }

        fun <Type> loading(data: Type? = null): ResourceModel<Type> {
            return ResourceModel(State.LOADING, data, null)
        }
    }

    enum class State {
        LOADING, SUCCESS, ERROR, DEFAULT
    }
}