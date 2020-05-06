package com.marvelcomics.brito.data.datasource.remote.utils

import com.marvelcomics.brito.infrastructure.exception.MarvelApiException
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class AuthHashGenerator {

    @Throws(MarvelApiException::class)
    fun generateHash(timestamp: String, publicKey: String, privateKey: String): String {
        return try {
            val value = timestamp + privateKey + publicKey
            val md5Encoder = MessageDigest.getInstance("MD5")
            val md5Bytes = md5Encoder.digest(value.toByteArray())
            BigInteger(1, md5Bytes).toString(16).padStart(32, '0')
        } catch (e: NoSuchAlgorithmException) {
            throw MarvelApiException("cannot generate the api key", e)
        }
    }
}