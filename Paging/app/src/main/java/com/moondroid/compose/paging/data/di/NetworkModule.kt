package com.moondroid.compose.paging.data.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.moondroid.compose.paging.data.api.GithubApiService
import com.moondroid.compose.paging.data.api.MovieApiService
import com.moondroid.compose.paging.data.datasource.RemoteDataSource
import com.moondroid.compose.paging.data.datasource.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    class ResponseInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val response = chain.proceed(chain.request())
            val modified = response.newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
                .build()
            return modified
        }
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor {
            val log = try {
                JSONObject(it).toString()
            } catch (e: JSONException) {
                //URLDecoder.decode(it, "UTF-8")
                it
            }
            Log.d("HttpClient", log)
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(ResponseInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp/")
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(GsonBuilder().create())


    @Provides
    @Singleton
    fun provideGithubApiService(retrofit: Retrofit): GithubApiService = retrofit.create(GithubApiService::class.java)

    @Provides
    @Singleton
    fun provideMovieApiService(retrofit: Retrofit): MovieApiService = retrofit.create(MovieApiService::class.java)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkBindModule {
    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource
}