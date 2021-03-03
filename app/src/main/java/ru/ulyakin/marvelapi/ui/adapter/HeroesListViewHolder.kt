package ru.ulyakin.marvelapi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelsapi.R
import ru.ulyakin.marvelapi.model.Hero
import ru.ulyakin.marvelapi.common.load

class HeroesListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.hero_name)
    private val image: ImageView = view.findViewById(R.id.hero_image)

//todo later
//    init {
//        view.setOnClickListener {
//            }
//        }
//    }

    fun bind(hero: Hero) {
        val heroImagePath: String = hero.thumbnail.getImageUrl()

        name.text = hero.name
        image.load(heroImagePath)
    }

    companion object {
        fun create(parent: ViewGroup): HeroesListViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hero, parent, false)
            return HeroesListViewHolder(view)
        }
    }
}