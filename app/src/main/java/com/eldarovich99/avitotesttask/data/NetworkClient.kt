package com.eldarovich99.avitotesttask.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkClient {
    companion object{

        private var builderMainManagerRetro: Retrofit.Builder? = null
        private const val BASE_URL = "https://raw.githubusercontent.com/avito-tech/android-trainee-task/master/"
        fun <T> getApi(api: Class<T>): T {
            return mainManagerRetroClient.create(api)
        }
        private val mainManagerRetroClient: Retrofit
            get() {

                //cookie.setCookie(null)
                val logging = HttpLoggingInterceptor()
                logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }

                val httpClient = OkHttpClient.Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    //.cookieJar(cookie)
                    .build()

                if (builderMainManagerRetro == null) {
                    builderMainManagerRetro = Retrofit.Builder()
                }
                return builderMainManagerRetro!!
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build()
            }
    }

}