package cal.calor.caloriecounter.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiFactory {
    companion object {
        private const val BASE_URL = "https://platform.fatsecret.com/rest/"
        private const val BASE_URL_AUTHORIZATION = "https://oauth.fatsecret.com/connect/"

        fun getApiAuthorization(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_AUTHORIZATION)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create()
        }
        fun getApi(token : String): ApiService {
            val clientBuilder = OkHttpClient.Builder()
            clientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            val client = clientBuilder.addInterceptor { chain ->
                val request =
                    chain.request().newBuilder().addHeader("Authorization", "Bearer ${token}")
                        .build()
                chain.proceed(request)
            }.build()

            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create()
        }
    }

}