package com.geekster.cryptotracker.NetworkModule

//import com.geekster.cryptotracker.api.apiMethod
import com.geekster.cryptotracker.api.CoinLayerService
import com.geekster.cryptotracker.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesService(retrofit: Retrofit): CoinLayerService {
        return retrofit.create(CoinLayerService::class.java)
    }
}