package com.example.my_application.feature_movielist.di

import com.example.my_application.feature_movielist.data.Constants.BASE_URL
import com.example.my_application.feature_movielist.data.ShowRepositoryImp
import com.example.my_application.feature_movielist.data.ShowsApi
import com.example.my_application.feature_movielist.domain.repository.ShowRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): ShowsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShowsApi::class.java)

    }

    @Provides
    @Singleton
    fun provideShowRepository(api: ShowsApi): ShowRepository {
        return ShowRepositoryImp(api)
    }
}



