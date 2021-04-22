package ru.ulyakin.marvelapi.common

import ru.ulyakin.marvelapi.data.mapper.HeroesMapper
import ru.ulyakin.marvelapi.data.model.Hero
import ru.ulyakin.marvelapi.data.model.MarvelCharacters

// TODO Remove; move to mapper
fun MarvelCharacters.asDomainModel(mapper: HeroesMapper): List<Hero> =
    data.results.map(mapper::map)