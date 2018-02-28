package co.midros.tmdb.di.modules

import co.midros.tmdb.api.NewsApi
import co.midros.tmdb.api.RestApi
import co.midros.tmdb.api.RestFull
import co.midros.tmdb.utils.ConstStrings.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by luis rafael on 19/02/18.
 */
@Module
class NetModule {

    @Provides
    fun provideNewsApi(api: RestApi): NewsApi = RestFull(api)

    @Provides
    fun provideRestApi(retrofit: Retrofit): RestApi = retrofit.create(RestApi::class.java)

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideRxJavaCallAdapterFactory(): CallAdapter.Factory
            = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

    @Provides
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory,callAdapterFactory: CallAdapter.Factory): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .build()
    }

}