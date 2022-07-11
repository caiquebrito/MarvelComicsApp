package com.marvelcomics.brito.data_remote.datasource.mapper

interface RemoteMapper<Input, Output> {
    fun transform(input: Input): Output
}