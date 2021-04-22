package ru.ulyakin.marvelapi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.ulyakin.marvelapi.data.model.Hero
import ru.ulyakin.marvelsapi.R

class HeroesListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.hero_name)
    private val image: ImageView = view.findViewById(R.id.hero_image)

    fun bind(hero: Hero, itemClickListener: OnItemClickListener) {
        val heroImagePath: String = hero.thumbnail.getImageUrl()
        name.text = hero.name

        image.load(heroImagePath) {
            crossfade(true)
        }

        view.setOnClickListener {
            itemClickListener.onItemClicked(hero)
        }
    }

    companion object {
        fun create(parent: ViewGroup): HeroesListViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hero, parent, false)
            return HeroesListViewHolder(view)
        }
    }
}