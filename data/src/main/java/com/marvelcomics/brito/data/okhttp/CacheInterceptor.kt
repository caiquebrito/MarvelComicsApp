package com.marvelcomics.brito.data.okhttp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class CacheInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original: Request = chain.request()

        original = if (hasNetwork(context)) {
            original.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
        } else {
            original.newBuilder().header(
                "Cache-Control",
                "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
            ).build()
        }

        return chain.proceed(original)
    }

    private fun hasNetwork(context: Context): Boolean {
        var isConnected: Boolean? = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

        if (activeNetwork != null && activeNetwork.isConnected) isConnected = true

        return isConnected ?: false
    }
}