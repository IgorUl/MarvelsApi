package com.example.marvelsapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelsapi.R
import com.example.marvelsapi.data.Hero

class HeroListAdapter(private var heroList: List<Hero>): RecyclerView.Adapter<HeroesListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroesListViewHolder {
        return HeroesListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_view, parent, false))
    }

    override fun getItemCount(): Int = heroList.size

    override fun onBindViewHolder(holder: HeroesListViewHolder, position: Int) {

        val hero: Hero = heroList[position]

        holder.heroName.text = hero.name
        holder.heroDescription.text = hero.description
    }
}