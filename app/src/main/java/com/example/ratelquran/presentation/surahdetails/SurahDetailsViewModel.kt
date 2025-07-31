package com.example.ratelquran.presentation.surahdetails


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ratelquran.domain.model.Ayah
import com.example.ratelquran.domain.usecase.GetVersesBySurahUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurahDetailsViewModel @Inject constructor(
    private val getVersesBySurahUseCase: GetVersesBySurahUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _ayahs = MutableStateFlow<List<Ayah>>(emptyList())
    val ayahs: StateFlow<List<Ayah>> = _ayahs

    init {
        val surahNumber = savedStateHandle.get<Int>("surahNumber") ?: 1
        loadSurahVerses(surahNumber)
    }

    fun loadSurahVerses(number: Int) {
        viewModelScope.launch {
            val verses = getVersesBySurahUseCase(number)
            _ayahs.value = verses
        }
    }
}
