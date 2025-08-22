package com.example.ratelquran.presentation.lastread

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.example.ratelquran.domain.model.LastRead
import com.example.ratelquran.domain.usecase.ClearLastReadUseCase
import com.example.ratelquran.domain.usecase.GetLastReadUseCase
import com.example.ratelquran.domain.usecase.SaveLastReadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class LastReadViewModel @Inject constructor(
    private val getLastReadUseCase: GetLastReadUseCase,
    private val saveLastReadUseCase: SaveLastReadUseCase,
    private val clearLastReadUseCase: ClearLastReadUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<LastRead?>(null)
    val uiState: StateFlow<LastRead?> = _uiState

    fun loadLastRead(onResult: ((LastRead?) -> Unit)? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val lastRead = getLastReadUseCase()
            _uiState.value = lastRead
            withContext(Dispatchers.Main) {
                onResult?.invoke(lastRead)
            }

        }
    }


    fun saveLastRead(surahNumber: Int, ayahNumber: Int, surahName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            saveLastReadUseCase(LastRead(surahNumber, ayahNumber, surahName))
            _uiState.value = LastRead(surahNumber, ayahNumber, surahName)
        }
    }

    fun clearLastRead() {
        viewModelScope.launch(Dispatchers.IO) {
            clearLastReadUseCase()
            _uiState.value = null
        }
    }
}