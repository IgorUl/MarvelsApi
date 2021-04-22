package ru.ulyakin.marvelapi.data.mapper

import ru.ulyakin.marvelapi.data.model.Hero

class HeroesMapper {
//    fun mapResponse(data: MarvelCharacters) -> List<Hero> // TODO

    // TODO Add new domain/presentation model and map to it
    // api : apiItem from backend
    // domain/presentation: model
    fun map(hero: Hero): Hero =
        Hero(hero.id, hero.name, hero.description, hero.thumbnail, hero.comics)
}