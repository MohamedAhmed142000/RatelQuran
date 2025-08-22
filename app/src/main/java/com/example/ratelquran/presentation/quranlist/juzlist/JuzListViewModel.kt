package com.example.ratelquran.presentation.quranlist.juzlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ratelquran.domain.model.Ayah
import com.example.ratelquran.domain.model.JuzModel
import com.example.ratelquran.domain.usecase.GetJuzListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class JuzListViewModel @Inject constructor(private val getJuzListUseCase: GetJuzListUseCase):
    ViewModel() {

    private val _juz= MutableStateFlow<List<JuzModel>>(emptyList())
    val juz: StateFlow<List<JuzModel>> =_juz



    init {
        loadJuz()
    }

    private fun loadJuz() {
        viewModelScope.launch {
            val list = getJuzListUseCase()
            _juz.value = list
        }
    }




}