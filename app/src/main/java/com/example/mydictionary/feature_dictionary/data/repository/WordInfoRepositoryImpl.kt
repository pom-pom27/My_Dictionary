package com.example.mydictionary.feature_dictionary.data.repository

import com.example.mydictionary.core.util.Resource
import com.example.mydictionary.feature_dictionary.data.local.WordInfoDao
import com.example.mydictionary.feature_dictionary.data.remote.DictionaryApi
import com.example.mydictionary.feature_dictionary.domain.models.WordInfo
import com.example.mydictionary.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

//repository is used to decide which data will be forward to viewmode and ui
class WordInfoRepositoryImpl(private val api: DictionaryApi, private val dao: WordInfoDao) :
    WordInfoRepository {
    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        val wordInfosFromDb = dao.getWordInfo(word).map { it.toWordInfo() }
        emit(Resource.Loading(data = wordInfosFromDb))


        try {

            //flow :  get the data from api if successful remove the older data then insert data form api
            val remoteWordInfos = api.getWordInfo(word)
            dao.deleteWordInfos(remoteWordInfos.map { it.word })
            dao.insertWordInfos(remoteWordInfos.map { it.toWordInfoEntity() })

        } catch (e: HttpException) {
            //get invalid response
            emit(Resource.Error(message = "No definitions found.", data = wordInfosFromDb))
        } catch (e: IOException) {
            // no internet connection, unreachable server
            emit(
                Resource.Error(
                    message = "Couldn't reach the server. check your internet connection.",
                    data = wordInfosFromDb
                )
            )

        }

        val newWordInfos = dao.getWordInfo(word).map { it.toWordInfo() }

        emit(Resource.Success(data = newWordInfos))
    }
}
