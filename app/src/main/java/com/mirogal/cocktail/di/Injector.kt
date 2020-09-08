package com.mirogal.cocktail.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.google.gson.GsonBuilder
import com.mirogal.cocktail.data.db.impl.CocktailAppRoomDatabase
import com.mirogal.cocktail.data.db.impl.dao.CocktailDao
import com.mirogal.cocktail.data.db.impl.dao.UserDao
import com.mirogal.cocktail.data.db.impl.dao.base.BaseDao
import com.mirogal.cocktail.data.db.impl.source.CocktailDbSourceImpl
import com.mirogal.cocktail.data.db.impl.source.UserDbSourceImpl
import com.mirogal.cocktail.data.db.source.CocktailDbSource
import com.mirogal.cocktail.data.db.source.UserDbSource
import com.mirogal.cocktail.data.db.source.base.BaseDbSource
import com.mirogal.cocktail.data.local.impl.SharedPrefsHelper
import com.mirogal.cocktail.data.local.impl.source.AppSettingLocalSourceImpl
import com.mirogal.cocktail.data.local.impl.source.TokenLocalSourceImpl
import com.mirogal.cocktail.data.local.source.AppSettingLocalSource
import com.mirogal.cocktail.data.local.source.TokenLocalSource
import com.mirogal.cocktail.data.network.impl.deserializer.BooleanDeserializer
import com.mirogal.cocktail.data.network.impl.deserializer.Iso8601DateDeserializer
import com.mirogal.cocktail.data.network.impl.deserializer.model.CocktailNetModelDeserializer
import com.mirogal.cocktail.data.network.impl.extension.deserializeType
import com.mirogal.cocktail.data.network.impl.interceptor.*
import com.mirogal.cocktail.data.network.impl.source.AuthNetSourceImpl
import com.mirogal.cocktail.data.network.impl.source.CocktailNetSourceImpl
import com.mirogal.cocktail.data.network.impl.source.UserNetSourceImpl
import com.mirogal.cocktail.data.network.impl.source.UserUploadNetSourceImpl
import com.mirogal.cocktail.data.network.model.cocktail.CocktailNetModel
import com.mirogal.cocktail.data.network.source.AuthNetSource
import com.mirogal.cocktail.data.network.source.CocktailNetSource
import com.mirogal.cocktail.data.network.source.UserNetSource
import com.mirogal.cocktail.data.network.source.UserUploadNetSource
import com.mirogal.cocktail.data.network.source.base.BaseNetSource
import com.mirogal.cocktail.data.repository.impl.mapper.*
import com.mirogal.cocktail.data.repository.impl.mapper.base.BaseRepoModelMapper
import com.mirogal.cocktail.data.repository.impl.source.*
import com.mirogal.cocktail.data.repository.source.*
import com.mirogal.cocktail.data.repository.source.base.BaseRepository
import com.mirogal.cocktail.extension.log
import com.mirogal.cocktail.presentation.mapper.CocktailModelMapper
import com.mirogal.cocktail.presentation.mapper.LocalizedStringModelMapper
import com.mirogal.cocktail.presentation.mapper.UserModelMapper
import com.mirogal.cocktail.presentation.mapper.base.BaseModelMapper
import com.mirogal.cocktail.presentation.ui.auth.AuthViewModel
import com.mirogal.cocktail.presentation.ui.detail.DetailViewModel
import com.mirogal.cocktail.presentation.ui.main.MainViewModel
import com.mirogal.cocktail.presentation.ui.main.drink.DrinkViewModel
import com.mirogal.cocktail.presentation.ui.main.profile.ProfileViewModel
import com.mirogal.cocktail.presentation.ui.main.settings.SettingsViewModel
import com.mirogal.cocktail.presentation.ui.search.SearchViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

object Injector {

    private lateinit var application: Application

    private val baseGsonBuilder: GsonBuilder
        get() = GsonBuilder()
                .registerTypeAdapter(deserializeType<Boolean>(), BooleanDeserializer(false))
                .registerTypeAdapter(deserializeType<Date>(), Iso8601DateDeserializer())
                .setPrettyPrinting()
                .serializeNulls()

    val retrofit by lazy {

        val provideRepository = provideRepository<TokenRepository>(application)

        provideRetrofit(
                application,
                "https://devlightschool.ew.r.appspot.com/",
                setOf(),
                setOf(
                        GsonConverterFactory.create(baseGsonBuilder.create())
                ),
                provideOkHttpClientBuilder(),
                TokenInterceptor { provideRepository.token }, AppVersionInterceptor(), PlatformInterceptor(), PlatformVersionInterceptor()
        )
    }

    val cocktailRetrofit by lazy {

        val provideRepository = provideRepository<TokenRepository>(application)

        provideRetrofit(
                application,
                "https://www.thecocktaildb.com/api/json/v1/1/",
                setOf(),
                setOf(
                        GsonConverterFactory.create(
                                baseGsonBuilder
                                        //register deserializer for cocktail list for only cocktail retrofit
                                        .registerTypeAdapter(
                                                deserializeType<CocktailNetModel>(),
                                                CocktailNetModelDeserializer()
                                        )
                                        .create()
                        )
                ),
                provideOkHttpClientBuilder(),
                TokenInterceptor { provideRepository.token }, AppVersionInterceptor(), PlatformInterceptor(), PlatformVersionInterceptor()
        )
    }

    val uploadRetrofit by lazy {

        val provideRepository = provideRepository<TokenRepository>(application)

        provideRetrofit(
                application,
                "https://devlightschool.ew.r.appspot.com/",
                setOf(),
                setOf(
                        GsonConverterFactory.create(baseGsonBuilder.create())
                ),
                provideOkHttpClientBuilder(writeTimeoutSeconds = TimeUnit.MINUTES.toSeconds(5L)),
                TokenInterceptor { provideRepository.token }, AppVersionInterceptor(), PlatformInterceptor(), PlatformVersionInterceptor()
        )
    }

    /**
     * Must be called at application class or other place (as soon as app starts before activities get created)
     */
    fun init(application: Application) {
//        require(applicationContext is Application) { "Context must be application context" }
        this.application = application
        //init database
        CocktailAppRoomDatabase.instance(application)
    }

    class ViewModelFactory(
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = (owner as? Activity)?.intent?.extras
                    ?: (owner as? Fragment)?.arguments
    ) : AbstractSavedStateViewModelFactory(
            owner,
            defaultArgs
    ) {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
        ): T {
            return when (modelClass) {
                AuthViewModel::class.java -> AuthViewModel(
                        provideRepository(application),
                        provideRepository(application),
                        provideModelMapper(application),
                        handle,
                        application
                ) as T

                MainViewModel::class.java -> MainViewModel(
                        provideRepository(application),
                        handle,
                        application
                ) as T

                SearchViewModel::class.java -> SearchViewModel(
                        provideRepository(application),
                        provideModelMapper(application),
                        handle,
                        application
                ) as T

                DetailViewModel::class.java -> DetailViewModel(
                        provideRepository(application),
                        provideModelMapper(application),
                        handle,
                        application
                ) as T

                DrinkViewModel::class.java -> DrinkViewModel(
                        provideRepository(application),
                        provideModelMapper(application),
                        handle,
                        application
                ) as T

                SettingsViewModel::class.java -> SettingsViewModel(
                        provideRepository(application),
                        handle,
                        application
                ) as T

                ProfileViewModel::class.java -> ProfileViewModel(
                        handle,
                        application
                ) as T

                else -> throw NotImplementedError("Must provide viewModel for class ${modelClass.simpleName}")
            }
        }
    }

    inline fun <reified T : BaseRepository> provideRepository(context: Context): T {
        "LOG provideRepository class = ${T::class.java.simpleName}".log
        return when (T::class.java) {
            CocktailRepository::class.java -> CocktailRepositoryImpl(
                    provideDbDataSource(context),
                    provideNetDataSource(context),
                    provideRepoModelMapper(context)
            ) as T

            UserRepository::class.java -> UserRepositoryImpl(
                    provideDbDataSource(context),
                    provideNetDataSource(context),
                    provideNetDataSource(context),
                    provideRepoModelMapper(context)
            ) as T

            AuthRepository::class.java -> AuthRepositoryImpl(
                    provideNetDataSource(context),
                    provideNetDataSource(context),
                    provideDbDataSource(context),
                    provideRepoModelMapper(context),
                    provideLocalDataSource(context)
            ) as T

            TokenRepository::class.java -> TokenRepositoryImpl(
                    provideLocalDataSource(context)
            ) as T

            AppSettingRepository::class.java -> AppSettingRepositoryImpl(
                    provideLocalDataSource(context)
            ) as T

            else -> throw IllegalStateException("Must provide repository for class ${T::class.java.simpleName}")
        }
    }

    inline fun <reified T : BaseDbSource> provideDbDataSource(context: Context): T {
        "LOG provideDbDataSource class = ${T::class.java.simpleName}".log
        return when (T::class.java) {
            UserDbSource::class.java -> UserDbSourceImpl(provideDao(context)) as T
            CocktailDbSource::class.java -> CocktailDbSourceImpl(provideDao(context)) as T
            else -> throw IllegalStateException("Must provide DbDataSource for class ${T::class.java.simpleName}")
        }
    }

    inline fun <reified T> provideLocalDataSource(context: Context): T {
        "LOG provideLocalDataSource class = ${T::class.java.simpleName}".log
        return when (T::class.java) {
            TokenLocalSource::class.java -> TokenLocalSourceImpl(
                    SharedPrefsHelper(
                            context.getSharedPreferences(
                                    "sp",
                                    Context.MODE_PRIVATE
                            )
                    )
            ) as T

            AppSettingLocalSource::class.java -> AppSettingLocalSourceImpl(
                    SharedPrefsHelper(
                            context.getSharedPreferences(
                                    "sp",
                                    Context.MODE_PRIVATE
                            )
                    )
            ) as T

            else -> throw IllegalStateException("Must provide LocalDataSource for class ${T::class.java.simpleName}")
        }
    }

    inline fun <reified T : BaseNetSource> provideNetDataSource(context: Context): T {
        "LOG provideNetDataSource class = ${T::class.java.simpleName}".log
        return when (T::class.java) {
            AuthNetSource::class.java -> AuthNetSourceImpl(provideService()) as T
            CocktailNetSource::class.java -> CocktailNetSourceImpl(provideCocktailService()) as T
            UserNetSource::class.java -> UserNetSourceImpl(provideService()) as T
            UserUploadNetSource::class.java -> UserUploadNetSourceImpl(context, provideUploadService()) as T
            else -> throw IllegalStateException("Must provide NetDataSource for class ${T::class.java.simpleName}")
        }
    }

    inline fun <reified T> provideCocktailService(): T {
        "LOG provideCocktailService class = ${T::class.java.simpleName}".log
        return cocktailRetrofit.create(T::class.java) as T
    }

    inline fun <reified T> provideService(): T {
        "LOG provideService class = ${T::class.java.simpleName}".log
        return retrofit.create(T::class.java) as T
    }

    inline fun <reified T> provideUploadService(): T {
        "LOG provideUploadService class = ${T::class.java.simpleName}".log
        return uploadRetrofit.create(T::class.java) as T
    }

    private fun provideRetrofit(
            context: Context,
            hostName: String,
            callAdapterFactories: Set<CallAdapter.Factory> = setOf(),
            converterFactories: Set<Converter.Factory> = setOf(),
            okHttpClientBuilder: OkHttpClient.Builder,
            vararg interceptors: Interceptor
    ): Retrofit {

        interceptors.forEach { okHttpClientBuilder.addInterceptor(it) }

        configureOkHttpInterceptors(context, okHttpClientBuilder)
        val builder = Retrofit.Builder()

        callAdapterFactories.forEach {
            builder.addCallAdapterFactory(it)
        }

        converterFactories.forEach {
            builder.addConverterFactory(it)
        }


        builder
                .client(okHttpClientBuilder.build())
                .baseUrl(hostName)

        return builder.build()
    }

    private fun configureOkHttpInterceptors(
            context: Context,
            okHttpClientBuilder: OkHttpClient.Builder
    ) {

        // OkHttp Logger
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addInterceptor(logger)

        // Postman Mock
        okHttpClientBuilder.addInterceptor(PostmanMockInterceptor())

        // Gander
//        okHttpClientBuilder.addInterceptor(
//                GanderInterceptor(context).apply {
//                    showNotification(true)
//                }
//        )
    }

    inline fun <reified T : BaseRepoModelMapper<*, *, *>> provideRepoModelMapper(context: Context): T {
        "LOG provideRepoModelMapper class = ${T::class.java.simpleName}".log
        return when (T::class.java) {
            CocktailRepoModelMapper::class.java -> CocktailRepoModelMapper(
                    provideNestedRepoModelMapper(context),
                    provideNestedRepoModelMapper(context),
                    provideNestedRepoModelMapper(context)
            )
            UserRepoModelMapper::class.java -> UserRepoModelMapper()
            else -> throw IllegalStateException("Must provide RepoModelMapper for class ${T::class.java.simpleName}")
        } as T
    }

    inline fun <reified T : BaseModelMapper<*, *>> provideModelMapper(context: Context): T {
        "LOG provideModelMapper class = ${T::class.java.simpleName}".log
        return when (T::class.java) {
            CocktailModelMapper::class.java -> CocktailModelMapper(provideNestedModelMapper(context))
            UserModelMapper::class.java -> UserModelMapper()
            else -> throw IllegalStateException("Must provide ModelMapper for class ${T::class.java.simpleName}")
        } as T
    }

    inline fun <reified T : BaseRepoModelMapper<*, *, *>> provideNestedRepoModelMapper(
            context: Context
    ): T {
        "LOG provideNestedRepoModelMapper class = ${T::class.java.simpleName}".log
        return when (T::class.java) {
            LocalizedStringRepoModelMapper::class.java -> LocalizedStringRepoModelMapper()
            CocktailNameRepoModelMapper::class.java -> CocktailNameRepoModelMapper()
            CocktailInstructionRepoModelMapper::class.java -> CocktailInstructionRepoModelMapper()
            else -> throw IllegalStateException("Must provide NestedRepoModelMapper for class ${T::class.java.simpleName}")
        } as T
    }

    inline fun <reified T : BaseModelMapper<*, *>> provideNestedModelMapper(context: Context): T {
        "LOG provideNestedModelMapper class = ${T::class.java.simpleName}".log
        return when (T::class.java) {
            LocalizedStringModelMapper::class.java -> LocalizedStringModelMapper()
            else -> throw IllegalStateException("Must provide NestedModelMapper for class ${T::class.java.simpleName}")
        } as T

    }

    inline fun <reified T : BaseDao<*>> provideDao(context: Context): T {
        "LOG provideDao class = ${T::class.java.simpleName}".log
        return when (T::class.java) {
            CocktailDao::class.java -> CocktailAppRoomDatabase.instance(context).cocktailDao()
            UserDao::class.java -> CocktailAppRoomDatabase.instance(context).userDao()
            else -> throw IllegalStateException("Must provide dao for class ${T::class.java.simpleName}")
        } as T
    }

    private fun provideOkHttpClientBuilder(
            readTimeoutSeconds: Long = 120,
            writeTimeoutSeconds: Long = 120
    ): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        try {
            val trustAllCerts = arrayOf(
                    object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                                chain: Array<X509Certificate>,
                                authType: String
                        ) = Unit

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                                chain: Array<X509Certificate>,
                                authType: String
                        ) = Unit

                        override fun getAcceptedIssuers(): Array<X509Certificate?> = emptyArray()
                    }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("TLS"/*TlsVersion.TLS_1_3.javaName*//*"SSL"*/)
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            builder.sslSocketFactory(sslSocketFactory, trustAllCerts.first() as X509TrustManager)

        } catch (e: Exception) { // should never happen
            e.printStackTrace()
        }

        return builder
                .hostnameVerifier({ _, _ -> true })
                .readTimeout(readTimeoutSeconds, TimeUnit.SECONDS)
                .writeTimeout(writeTimeoutSeconds, TimeUnit.SECONDS)
    }

}