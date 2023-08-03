package com.hesham.ecommerceapp.di

import androidx.preference.PreferenceManager
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hesham.ecommerceapp.di.DependencyInjection.ECOMMERCE_CLIENT
import com.hesham.ecommerceapp.di.DependencyInjection.ECOMMERCE_INTERCEPTOR
import com.hesham.ecommerceapp.di.DependencyInjection.ECOMMERCE_RETROFIT
import com.hesham.ecommerceapp.domain.gateways.local.AppDatabase
import com.hesham.ecommerceapp.domain.gateways.local.LocalGateway
import com.hesham.ecommerceapp.domain.gateways.local.LocalGatewayImplementation
import com.hesham.ecommerceapp.domain.gateways.remote.EcommerceGateway
import com.hesham.ecommerceapp.domain.gateways.remote.OnMarketHeaderInterceptor
import com.hesham.ecommerceapp.domain.repositories.AuthRepository
import com.hesham.ecommerceapp.domain.repositories.AuthRepositoryImplementation
import com.hesham.ecommerceapp.domain.repositories.CartRepository
import com.hesham.ecommerceapp.domain.repositories.CartRepositoryImplementation
import com.hesham.ecommerceapp.domain.repositories.ProductRepository
import com.hesham.ecommerceapp.domain.repositories.ProductRepositoryImplementation
import com.hesham.ecommerceapp.ui.auth.authModule
import com.hesham.ecommerceapp.ui.home.homeModule
import com.hesham.ecommerceapp.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

enum class DependencyInjection {
    ECOMMERCE_CLIENT, ECOMMERCE_RETROFIT, ECOMMERCE_INTERCEPTOR
}

val applicationModule = module {
    single { PreferenceManager.getDefaultSharedPreferences(get()) }
    single<Gson> {
        GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create()
    }
    single<LocalGateway> { LocalGatewayImplementation(get()) }

    factory { Job() }
    factory { CoroutineScope(Dispatchers.IO + get<Job>()) }
}

val databaseModule = module{
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "ecommerce_database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
    single { get<AppDatabase>().productDto() }
}

val ecommerceGatewayModule = module {
    single(named(ECOMMERCE_INTERCEPTOR)) { OnMarketHeaderInterceptor(get()) }
    single(named(ECOMMERCE_CLIENT)) {
        OkHttpClient.Builder()
            .addInterceptor(get<OnMarketHeaderInterceptor>(named(ECOMMERCE_INTERCEPTOR)))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(15, TimeUnit.MINUTES).writeTimeout(15, TimeUnit.MINUTES)
            .readTimeout(15, TimeUnit.MINUTES).callTimeout(15, TimeUnit.MINUTES).build()
    }

    single<EcommerceGateway>(named(ECOMMERCE_RETROFIT)) {
        Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .baseUrl(Constants.BASE_URL)
            .client(get(named(ECOMMERCE_CLIENT)))
            .build()
            .create(EcommerceGateway::class.java)
    }
}


val repositoriesModule = module {
    single<AuthRepository> { AuthRepositoryImplementation(get(named(ECOMMERCE_RETROFIT)),get()) }
    single<ProductRepository> { ProductRepositoryImplementation(get(named(ECOMMERCE_RETROFIT))) }
    single<CartRepository> { CartRepositoryImplementation(get()) }

}


val featuresModule = listOf(
    authModule(),
    homeModule(),
)

val applicationModules = listOf(
    applicationModule,
    databaseModule,
    ecommerceGatewayModule,
    repositoriesModule,
) + featuresModule