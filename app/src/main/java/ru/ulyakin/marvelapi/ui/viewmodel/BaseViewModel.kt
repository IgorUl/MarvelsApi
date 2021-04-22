package ru.ulyakin.marvelapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import ru.ulyakin.marvelapi.data.api.ApiInterface

class BaseViewModel(private val service: ApiInterface): ViewModel() {


}