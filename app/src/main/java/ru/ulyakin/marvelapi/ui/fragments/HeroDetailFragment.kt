package ru.ulyakin.marvelapi.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.ulyakin.marvelapi.ui.viewmodel.HeroDetailViewModel
import ru.ulyakin.marvelsapi.R

class HeroDetailFragment: Fragment(R.layout.fragment_hero_detail) {

    private lateinit var viewModel: HeroDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //todo добавь в зависимость safeargs


    }

}