package com.example.ratelquran.presentation.tafsir

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ratelquran.domain.model.Tafsir
import com.example.ratelquran.domain.usecase.GetTafsirUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class TafsirViewModel @Inject constructor(
    private val getTafsirForAyahUseCase: GetTafsirUseCase
) : ViewModel() {

    private val _tafsirState = MutableStateFlow<Tafsir?>(null)
    val tafsirState: StateFlow<Tafsir?> = _tafsirState.asStateFlow()

    fun loadTafsir(surahNumber: Int, ayahNumber: Int) {
        viewModelScope.launch {
            val tafsir = getTafsirForAyahUseCase(surahNumber, ayahNumber)
            _tafsirState.value = tafsir
        }
    }

    fun clearTafsir() {
        _tafsirState.value = null
    }
}
