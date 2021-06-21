package com.example.youthcare

import android.content.Context
import com.example.youthcare.repository.models.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


interface ApiInterface {
    @Headers("Content-Type:application/json")
    @POST("/api/UserAuth/login")
    fun signin(@Body info: SignInBody): retrofit2.Call<LoginResponse>

    @Headers("Content-Type:application/json")
    @POST("/api/UserAuth/registration")
    fun registerUser(
            @Body info: UserBody
    ): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @GET("/api/UserProfile")
    fun getInfoSportsman(@Header("Authorization") accessToken: String): retrofit2.Call<SportsmanData>


    @Headers("Content-Type:application/json")
    @PUT("/api/Users")
    fun updateInfoSportsman(@Body resd: SportsmanUpdateResponse): retrofit2.Call<SportsmanUpdateResponse>

    @Headers("Content-Type:application/json")
    @GET("/api/Users")
    fun getAllUsers(): retrofit2.Call<ArrayList<UserResponse>>

    @Headers("Content-Type:application/json")
    @DELETE("/api/Users/{id}")
    fun deleteCurrentUser(@Path("id") id: String): retrofit2.Call<Void>

    @Headers("Content-Type:application/json")
    @GET("/api/Section")
    fun getSections(): retrofit2.Call<ArrayList<Section>>

    @Headers("Content-Type:application/json")
    @POST("/api/SportsmanNote")
    fun createNote(@Body info: NoteData): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @GET("/api/SportsmanNote/{id}")
    fun getNotes(@Path("id") id: String): retrofit2.Call<java.util.ArrayList<NoteData>>

}

fun unSafeOkHttpClient() :OkHttpClient.Builder {
    val okHttpClient = OkHttpClient.Builder()
    try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts:  Array<TrustManager> = arrayOf(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        })

        // Install the all-trusting trust manager
        val  sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory
        if (trustAllCerts.isNotEmpty() &&  trustAllCerts.first() is X509TrustManager) {
            okHttpClient.sslSocketFactory(sslSocketFactory, trustAllCerts.first() as X509TrustManager)
            okHttpClient.hostnameVerifier { _, _ -> true}
        }

        return okHttpClient
    } catch (e: Exception) {
        return okHttpClient
    }

}
 class HttpsTrustManager : X509TrustManager {
    @Throws(CertificateException::class)
    override fun checkClientTrusted(
            x509Certificates: Array<X509Certificate?>?, s: String?) {
    }

    @Throws(CertificateException::class)
    override fun checkServerTrusted(
            x509Certificates: Array<X509Certificate?>?, s: String?) {
    }

     override fun getAcceptedIssuers(): Array<X509Certificate> {
         TODO("Not yet implemented")
     }

     fun isClientTrusted(chain: Array<X509Certificate?>?): Boolean {
        return true
    }

    fun isServerTrusted(chain: Array<X509Certificate?>?): Boolean {
        return true
    }

    val acceptedIssuers: Array<Any>
        get() = arrayOf(_AcceptedIssuers)

    companion object {
        private var trustManagers: Array<TrustManager>? = null
        private val _AcceptedIssuers: Array<X509Certificate> = arrayOf<X509Certificate>()

        fun allowAllSSL() {
            HttpsURLConnection.setDefaultHostnameVerifier(object : HostnameVerifier {
                override fun verify(arg0: String?, arg1: SSLSession?): Boolean {
                    return true
                }
            })
            var context: SSLContext? = null
            if (trustManagers == null) {
                trustManagers = arrayOf(HttpsTrustManager())
            }
            try {
                context = SSLContext.getInstance("TLS")
                context.init(null, trustManagers, SecureRandom())
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: KeyManagementException) {
                e.printStackTrace()
            }
            HttpsURLConnection.setDefaultSSLSocketFactory(context
                    ?.socketFactory)
        }
    }
}

fun trustAllCertificates() {
    try {
        val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                        return arrayOfNulls(0)
                    }

                    override fun checkClientTrusted(certs: Array<X509Certificate?>?, authType: String?) {}
                    override fun checkServerTrusted(certs: Array<X509Certificate?>?, authType: String?) {}
                }
        )
        val sc = SSLContext.getInstance("SSL")
        sc.init(null, trustAllCerts, SecureRandom())
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
        HttpsURLConnection.setDefaultHostnameVerifier { arg0, arg1 -> true }
    } catch (e: java.lang.Exception) {
    }
}
class RetrofitInstance {

    companion object {
        val BASE_URL: String = "https://youth-care.azurewebsites.net/"

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            trustAllCertificates()
            unSafeOkHttpClient()
            this.level = HttpLoggingInterceptor.Level.BODY
        }


        val client: OkHttpClient = OkHttpClient.Builder().apply {
            trustAllCertificates()
            unSafeOkHttpClient()
            this.addInterceptor(interceptor)
        }.connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build()

        fun getRetrofitInstance(context: Context): Retrofit {
            unSafeOkHttpClient()
            trustAllCertificates()
            HttpsTrustManager.allowAllSSL()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

    }
}

class  RetrofitSections{
    companion object {
        val BASE_URL: String = "https://youth-care.azurewebsites.net/api/"

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            trustAllCertificates()
            unSafeOkHttpClient()
            this.level = HttpLoggingInterceptor.Level.BODY
        }


        val client: OkHttpClient = OkHttpClient.Builder().apply {
            trustAllCertificates()
            unSafeOkHttpClient()
            this.addInterceptor(interceptor)
        }.connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()

        fun getRetrofitInstance(context: Context): Retrofit {
            unSafeOkHttpClient()
            trustAllCertificates()
            HttpsTrustManager.allowAllSSL()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

    }
}

