package com.example.ratelquran.presentation.juzfull


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ratelquran.domain.model.Ayah
import com.example.ratelquran.domain.model.JuzModel
import com.example.ratelquran.domain.usecase.GetVersesByJuzUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JuzFullViewModel @Inject constructor(
    private val getVersesByJuzUseCase: GetVersesByJuzUseCase
) : ViewModel() {

    private val _verses = MutableStateFlow<List<Ayah>>(emptyList())
    val verses: StateFlow<List<Ayah>> = _verses

    fun loadVerses(juzNumber: JuzModel) {
        viewModelScope.launch {
            _verses.value = getVersesByJuzUseCase(juzNumber)
        }
        Log.d("JUZ_VIEWMODEL", "Loaded verses count = ${_verses.value.size}")
        _verses.value.take(5).forEach {
            Log.d("JUZ_VIEWMODEL", "VerseDTO: chapter=${it.chapter}, verse=${it.verse}, text=${it.text.take(15)}")
        }

    }

}
