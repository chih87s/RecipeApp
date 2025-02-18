package com.db.recipeapp.ui.viewmodels

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SnackBarViewModel @Inject constructor() : ViewModel() {

    private val _snackBarMessage = MutableStateFlow<SnackBarMessage>(SnackBarMessage.Idle)
    val snackBarMessage: StateFlow<SnackBarMessage> = _snackBarMessage

    fun showErrorMessage(message: String) {
        if (_snackBarMessage.value is SnackBarMessage.Idle) {
            viewModelScope.launch {
                _snackBarMessage.emit(SnackBarMessage.Error(message))
            }
        }
    }


    fun clearSnackBarMessage() {
        viewModelScope.launch {
            _snackBarMessage.emit(SnackBarMessage.Idle)
        }
    }


}

sealed class SnackBarMessage{
    data object Idle:SnackBarMessage()
    data class Error(val message: String) : SnackBarMessage()
}