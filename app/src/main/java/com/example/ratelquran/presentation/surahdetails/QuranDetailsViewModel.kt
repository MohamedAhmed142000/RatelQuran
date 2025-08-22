package com.example.ratelquran.presentation.surahdetails


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ratelquran.domain.model.Ayah
import com.example.ratelquran.domain.model.JuzModel
import com.example.ratelquran.domain.repository.QuranRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuranDetailsViewModel @Inject constructor(
    private val repository: QuranRepository
) : ViewModel() {

    private val _verses = MutableStateFlow<List<Ayah>>(emptyList())
    val verses: StateFlow<List<Ayah>> get() = _verses

    fun loadSurahVerses(surahNumber: Int) {
        viewModelScope.launch {
            _verses.value = repository.getVersesBySurah(surahNumber)
        }
    }
    fun loadVerses(mode: QuranDetailsType, identifier: Any) {
        when (mode) {
            QuranDetailsType.SURAH -> if (identifier is Int) loadSurahVerses(identifier)
            QuranDetailsType.JUZ -> if (identifier is JuzModel) loadJuzVerses(identifier)
        }
    }

    fun loadJuzVerses(juzNumber: JuzModel) {
        viewModelScope.launch {
            _verses.value = repository.getVersesByJuz(juzNumber)
        }
    }
}
