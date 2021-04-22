package ru.ulyakin.marvelapi.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ru.ulyakin.marvelapi.data.model.Hero
import ru.ulyakin.marvelapi.data.api.ApiInterface
import kotlinx.coroutines.flow.Flow
import ru.ulyakin.marvelapi.data.MarvelPagingSource
import ru.ulyakin.marvelapi.data.mapper.HeroesMapper

class MarvelRepository(private val service: ApiInterface, private val mapper: HeroesMapper) {

    fun fetchHeroList(): Flow<PagingData<Hero>> =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ), pagingSourceFactory = {
                MarvelPagingSource(service, mapper)
            }
        ).flow

    suspend fun fetchHeroDetail(id: Int): Hero {
        return service.getCharacterDetail(id)
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}