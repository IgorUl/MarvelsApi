package ru.ulyakin.marvelapi.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ru.ulyakin.marvelapi.model.Hero
import ru.ulyakin.marvelapi.api.ApiInterface
import kotlinx.coroutines.flow.Flow

class MarvelRepository(private val service: ApiInterface, private val mapper: HeroesMapper) {

    fun getHeroesList(): Flow<PagingData<Hero>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ), pagingSourceFactory = { MarvelPagingSource(service, mapper) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}