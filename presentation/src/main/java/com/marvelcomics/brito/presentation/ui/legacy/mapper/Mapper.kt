package com.marvelcomics.brito.presentation.ui.legacy.mapper

interface Mapper<E, D> {
    fun toDomain(entity: E): D
    fun fromDomain(domain: D): E
}
