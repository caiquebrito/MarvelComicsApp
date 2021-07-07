package com.marvelcomics.brito.data.ktor

import android.content.Context
import android.util.Log
import com.marvelcomics.brito.data.okhttp.CacheInterceptor
import com.marvelcomics.brito.data.okhttp.KeyHashInterceptor
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.engine.okhttp.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.CertificatePinner
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

object NetworkModuleK {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = false
    }

    val ktorModule = module {
        single { get<Context>().cacheDir }
        single { Cache(directory = get(), maxSize = (5 * 1024 * 1024).toLong()) }
        single {
            HttpClient(OkHttp) {
                install(JsonFeature) {
                    acceptContentTypes = listOf(ContentType.Application.Any)
                    serializer = KotlinxSerializer(json)
                }
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            Log.v("Ktor", message)
                        }
                    }
                    level = LogLevel.ALL
                }
                defaultRequest {
                    this.url.host = BASE_URL
                    this.url.protocol = URLProtocol.HTTPS
                }
                engine {
                    config {
                        connectTimeout(60, TimeUnit.SECONDS)
                        readTimeout(60, TimeUnit.SECONDS)
                        cache(cache = get())
                        certificatePinner(certificatePinner)
                    }
                    addInterceptor(KeyHashInterceptor())
                    addInterceptor(CacheInterceptor(context = get()))
                }
            }
        }
    }

    val certificatePinner = CertificatePinner.Builder().add(
        "marvel.com",
        "sha256/79127babc1d3625ae552c54b34fdb53217137cefbce66f506bc9831cac6b3c4f"
    ).build()

    private const val BASE_URL = "gateway.marvel.com/v1/public"
}