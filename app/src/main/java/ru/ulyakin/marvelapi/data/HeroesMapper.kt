package ru.ulyakin.marvelapi.data

import ru.ulyakin.marvelapi.model.Hero

class HeroesMapper {
    fun map(hero: Hero): Hero =
        Hero(hero.id, hero.name, hero.description, hero.thumbnail)
}