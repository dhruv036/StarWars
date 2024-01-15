package io.dhruv.starwars.di


import dagger.Module
import dagger.Provides
import io.dhruv.starwars.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule{
    val BASE_URL = "https://swapi.dev/api/"

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}





//
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
////
//object ApiClientt {
//
//    val apiService: ApiService by lazy {
//        RetrofitClient.retrofit.create(ApiService::class.java)
//    }
//
//    //}
////
//    object RetrofitClient {
//        private const val BASE_URL = "https://swapi.dev/api/"
//
//        val retrofit: Retrofit by lazy {
//            Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//        }
//    }
//}