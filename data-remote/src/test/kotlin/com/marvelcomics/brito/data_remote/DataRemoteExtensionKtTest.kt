package com.marvelcomics.brito.data_remote

import com.marvelcomics.brito.data_remote.datasource.response.ThumbnailResponse
import com.marvelcomics.brito.data_remote.exception.ErrorBodyParseException
import com.marvelcomics.brito.data_remote.exception.MappingCodeNotFound
import com.marvelcomics.brito.data_remote.exception.NullBodyException
import com.marvelcomics.brito.data_remote.extensions.getBodyOrThrow
import com.marvelcomics.brito.data_remote.extensions.handleApi
import com.marvelcomics.brito.data_remote.extensions.handleFlowApi
import com.marvelcomics.brito.data_remote.extensions.mapCoraExceptionOrThrow
import com.marvelcomics.brito.data_remote.extensions.parseHttpExceptionOrThrow
import com.marvelcomics.brito.data_remote.extensions.throwIfNull
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
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
        // just to match a Nullable object and respect reified rules
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
        handleApi(
            callHandling = {
                throw Throwable()
            },
            errorHandling = {
                throwable = it
            }
        )
        assert(throwable != null) { "Should check throws call trigger" }
    }

    @Test(expected = MappingCodeNotFound::class)
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
                throwable.parseHttpExceptionOrThrow().mapCoraExceptionOrThrow(mappedCodes)
            }
        )
    }

    @Test
    fun `GIVEN received a object WHEN tried to get its value THEN return destiny object`() {
        val result = getObjectOrNull("10").throwIfNull()
        assert(result.isNotEmpty())
    }

    @Test(expected = NullPointerException::class)
    fun `GIVEN received a object WHEN tried to get its value THEN return a exception of nullable`() {
        getObjectOrNull(null).throwIfNull()
    }

    @Test
    fun `GIVEN execution call WHEN the return is a success THEN check flow result`() = runTest {
        val thumbnailResponse = ThumbnailResponse()
        thumbnailResponse.path = "www.teste.com"
        thumbnailResponse.extension = ".jpeg"
        val response = Response.success(thumbnailResponse)
        var body: Any? = null
        handleFlowApi { body = response.getBodyOrThrow() }.collect()
        assert(body is ThumbnailResponse) { "Should return the Response from extension method" }
    }

    @Test(expected = UnitTestException::class)
    fun `GIVEN a execution of flow WHEN there is mapped throwables THEN return the selected custom throwable`() =
        runTest {
            val jsonBody = "{\n" +
                "\t\"http_status_code\": 400,\n" +
                "\t\"message\": \"error mapped\",\n" +
                "\t\"code\": \"SMB-0012\"\n" +
                "}"
            val responseBody = ResponseBody.create(null, jsonBody)
            val response = Response.error<ResponseBody>(400, responseBody)
            val mappedCodes = mapOf<String, Throwable>(
                Pair("SMB-0012", UnitTestException())
            )
            handleFlowApi(mappedCodes) { response.getBodyOrThrow() }.collect()
        }

    @Test(expected = MappingCodeNotFound::class)
    fun `GIVEN a execution of flow WHEN code is not mapped THEN throw MappingCodeNotFound`() =
        runTest {
            val jsonBody = "{\n" +
                "\t\"http_status_code\": 400,\n" +
                "\t\"message\": \"error mapped\",\n" +
                "\t\"code\": \"ABC-1010\"\n" +
                "}"
            val responseBody = ResponseBody.create(null, jsonBody)
            val response = Response.error<ResponseBody>(400, responseBody)
            handleFlowApi { response.getBodyOrThrow() }.collect()
        }

    @Test(expected = ErrorBodyParseException::class)
    fun `GIVEN a execution of flow WHEN code is not mapped THEN throw ErrorBodyParseException`() =
        runTest {
            val jsonBody = "{\n" +
                "\t\"test\": 400,\n" +
                "\t\"testOne\": \"error mapped\",\n" +
                "\t\"testTwo\": \"ABC-1010\"\n" +
                "}"
            val responseBody = ResponseBody.create(null, jsonBody)
            val response = Response.error<ResponseBody>(400, responseBody)
            handleFlowApi { response.getBodyOrThrow() }.collect()
        }

    @Test(expected = HttpException::class)
    fun `GIVEN a execution of flow WHEN code is not mapped THEN throw ErrorBodyParseExddception`() =
        runTest {
            val jsonBody = ""
            val responseBody = ResponseBody.create(null, jsonBody)
            val response = Response.error<ResponseBody>(404, responseBody)
            handleFlowApi(
                callHandling = {
                    response.getBodyOrThrow()
                },
                errorHandling = {
                    assert((it as HttpException).code() == 404)
                    throw it
                }
            ).collect()
        }

    private fun getObjectOrNull(param: String?): List<String>? {
        return param?.let { listOf("0") }
    }
}

class UnitTestException : Exception()
