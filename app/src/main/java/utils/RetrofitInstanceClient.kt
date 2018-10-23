package utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientInstance {
    private lateinit var retrofit: Retrofit

    val retrofitInstance: Retrofit
        get() {
            retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(Constants.GIT_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit
        }
}