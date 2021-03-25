package ru.ulyakin.marvelapi.data.mapper

import ru.ulyakin.marvelapi.data.model.Hero

class HeroesMapper {
    fun map(hero: Hero): Hero =
        Hero(hero.id, hero.name, hero.description, hero.thumbnail)
}