package com.example.ratelquran.presentation.surahlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ratelquran.domain.model.SurahInfo
import com.example.ratelquran.domain.usecase.GetAllSurahsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurahListViewModel @Inject constructor(private val getAllSurahsUseCase: GetAllSurahsUseCase) :
    ViewModel() {
    private val _surah = MutableStateFlow<List<SurahInfo>>(emptyList())
    val surahs: StateFlow<List<SurahInfo>> = _surah

    init {
        loadSurah()
    }

    private fun loadSurah() {
        viewModelScope.launch {
            val list = getAllSurahsUseCase()
            _surah.value = list
        }
    }

}