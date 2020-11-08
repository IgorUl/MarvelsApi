package com.example.marvelsapi.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelsapi.R
import com.example.marvelsapi.data.Hero
import com.example.marvelsapi.fragments.MainFragment

class HeroListAdapter(private var heroList: List<Hero>, private val fragment: Fragment) :
    RecyclerView.Adapter<HeroesListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroesListViewHolder {
        return HeroesListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view, parent, false)
        )
    }

    override fun getItemCount(): Int = heroList.size

    override fun onBindViewHolder(holder: HeroesListViewHolder, position: Int) {

        val hero: Hero = heroList[position]

        val heroImagePath: String = hero.thumbnail.path + "/standard_medium." + hero.thumbnail.extension

        //todo fix http

        Glide.with(fragment)
            .load(heroImagePath)
            .into(holder.heroImage)

        holder.heroName.text = hero.name
        holder.heroDescription.text = hero.description
    }
}