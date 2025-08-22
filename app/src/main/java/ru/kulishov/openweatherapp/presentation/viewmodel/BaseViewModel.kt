package ru.kulishov.openweatherapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

abstract class BaseViewModel() : ViewModel() {
    val coroutineScope: CoroutineScope = viewModelScope

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }

    fun launch(block: suspend CoroutineScope.() -> Unit): Job = coroutineScope.launch { block() }
}