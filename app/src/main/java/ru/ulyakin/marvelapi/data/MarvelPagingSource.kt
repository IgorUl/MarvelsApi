package ru.ulyakin.marvelapi.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.ulyakin.marvelapi.data.model.Hero
import ru.ulyakin.marvelapi.data.api.ApiInterface
import retrofit2.HttpException
import ru.ulyakin.marvelapi.common.asDomainModel
import ru.ulyakin.marvelapi.data.mapper.HeroesMapper
import java.io.IOException

class MarvelPagingSource(private val service: ApiInterface, private val mapper: HeroesMapper) :
    PagingSource<Int, Hero>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hero> {
        val position = params.key ?: MARVEL_API_STARTING_PAGE_INDEX
        val offset = position * NETWORK_PAGE_SIZE

        return try {
            val response = service.getCharacter(offset).asDomainModel(mapper)
            val nextKey = if (response.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = response,
                prevKey = if (position == MARVEL_API_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Hero>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.id
        }
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
        private const val MARVEL_API_STARTING_PAGE_INDEX = 0
    }
}