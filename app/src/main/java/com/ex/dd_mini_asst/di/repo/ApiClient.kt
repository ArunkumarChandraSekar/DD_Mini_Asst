package com.ex.dd_mini_asst.di.repo


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object Factory {

        var retrofit: Retrofit? = null

        fun create(

        ): ApiInterface {
            val baseUrl = "https://616f12ac715a630017b39ac4.mockapi.io/"


            /**set your desired log level**/
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
                .callTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(90, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
            /** add your other interceptors …**/

            /** add logging as last interceptor
             *  this is the important line!
             **/
            httpClient.addInterceptor(logging)
//            httpClient.addInterceptor(MockClient(HealthNxtApp.appContext))

            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .method(original.method(), original.body())
                chain.proceed(request.build())
            }



            /**creating the retrofit instance and set the the base url**/
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()

            return retrofit?.create(ApiInterface::class.java)!!

        }



    }

}