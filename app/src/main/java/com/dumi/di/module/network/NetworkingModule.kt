package com.dumi.di.module.network

import android.app.Application
import com.dumi.networking.interceptor.OK_HTTP_CLIENT_TIMEOUT
import com.dumi.networking.interceptor.RequestInterceptor
import com.dumi.networking.repository.WordsApi
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkingModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor
    }

    @Singleton
    @Provides
    fun provideChuckInterceptor(app: Application): ChuckInterceptor {
        return ChuckInterceptor(app)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        chuckInterceptor: ChuckInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(chuckInterceptor)
            .addInterceptor(RequestInterceptor)
            .connectTimeout(OK_HTTP_CLIENT_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(OK_HTTP_CLIENT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(OK_HTTP_CLIENT_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gsonBuilder = GsonBuilder()

        val gson = gsonBuilder.create()
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideWordsRepository(retrofit: Retrofit): WordsApi {
        return retrofit.create(WordsApi::class.java)
    }
}