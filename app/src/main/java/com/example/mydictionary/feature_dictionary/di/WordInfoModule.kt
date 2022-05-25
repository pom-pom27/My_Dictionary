package com.example.mydictionary.feature_dictionary.di

import android.app.Application
import androidx.room.Room
import com.example.mydictionary.feature_dictionary.data.local.Converters
import com.example.mydictionary.feature_dictionary.data.local.entities.WordInfoDatabase
import com.example.mydictionary.feature_dictionary.data.remote.DictionaryApi
import com.example.mydictionary.feature_dictionary.data.repository.WordInfoRepositoryImpl
import com.example.mydictionary.feature_dictionary.data.util.GsonParser
import com.example.mydictionary.feature_dictionary.domain.repository.WordInfoRepository
import com.example.mydictionary.feature_dictionary.domain.use_case.GetWordInfo
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {
    @Provides
    @Singleton
    fun provideGetWordInfoUseCase(repository: WordInfoRepository): GetWordInfo =
        GetWordInfo(repository)

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        dictionaryApi: DictionaryApi,
        db: WordInfoDatabase
    ): WordInfoRepository =
        WordInfoRepositoryImpl(dictionaryApi, db.dao)

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi = Retrofit.Builder()
        .baseUrl(DictionaryApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DictionaryApi::class.java)


    //? change converter here
    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): WordInfoDatabase = Room.databaseBuilder(
        app, WordInfoDatabase::class.java, "word_db"
    ).addTypeConverter(Converters(GsonParser(Gson())))
        .build()

}
