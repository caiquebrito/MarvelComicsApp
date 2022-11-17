package com.marvelcomics.brito.data_remote

import com.marvelcomics.brito.data_remote.datasource.response.ThumbnailResponse
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

internal class DataRemoteExtensionKtTest {

    @Test
    fun `GIVEN a success response WHEN the extension extractor is called THEN return the object`() {
        val thumbnailResponse = ThumbnailResponse()
        thumbnailResponse.path = "www.teste.com"
        thumbnailResponse.extension = ".jpeg"
        val response = Response.success(thumbnailResponse)
        val body = response.getBodyOrThrow()
        assert(body is ThumbnailResponse) { "Should return the Response from extension method" }
    }

    @Test(expected = NullBodyException::class)
    fun `GIVEN a success response body null WHEN the extension extractor is called THEN throw a error`() {
        //just to match a Nullable object and respect reified rules
        val nullAny: Any? = null
        val response = Response.success(nullAny)
        response.getBodyOrThrow()
    }

    @Test(expected = HttpException::class)
    fun `GIVEN a error response WHEN the extension extractor is called THEN throw a error`() {
        val responseBody = ResponseBody.create(null, "{error}")
        val response = Response.error<ResponseBody>(404, responseBody)
        response.getBodyOrThrow()
    }

    @Test
    fun `GIVEN a function call WHEN inline is executed THEN returns logic result itself`() {
        val callFunction = handleApi {
            2 + 2
        }
        assert(callFunction == 4) { "Should return executed unit block code" }
    }

    @Test
    fun `GIVEN a function call WHEN suspended function execute THEN throws itself`() {
        var throwable: Throwable? = null
        handleApi(callHandling = {
            throw Throwable()
        }, errorHandling = {
            throwable = it
        })
        assert(throwable != null) { "Should check throws call trigger" }
    }

    @Test(expected = ErrorBodyException::class)
    fun `GIVEN a function call WHEN the responseBody is error and is mapped THEN throws a mapped Exception`() {
        val jsonBody = "{\n" +
                "\t\"http_status_code\": 400,\n" +
                "\t\"message\": \"error mapped\",\n" +
                "\t\"code\": \"SMB-0101\"\n" +
                "}"
        val responseBody = ResponseBody.create(null, jsonBody)
        val response = Response.error<ResponseBody>(400, responseBody)
        handleApi {
            response.getBodyOrThrow()
        }
    }

    @Test(expected = Exception::class)
    fun `GIVEN a function call WHEN the responseBody is error and is mapped THEN throws a mapped coded Exception`() {
        val jsonBody = "{\n" +
                "\t\"http_status_code\": 400,\n" +
                "\t\"message\": \"error mapped\",\n" +
                "\t\"code\": \"SMB-0101\"\n" +
                "}"
        val responseBody = ResponseBody.create(null, jsonBody)
        val response = Response.error<ResponseBody>(400, responseBody)
        handleApi(
            callHandling = {
                response.getBodyOrThrow()
            },
            errorHandling = { throwable ->
                val mappedCodes = hashMapOf<String, Exception>(
                    Pair("SMB-0101", UnitTestException())
                )
                throwable.handledByCommon<Throwable>().handleByCode(mappedCodes)
            }
        )
    }
}

class UnitTestException : Exception()