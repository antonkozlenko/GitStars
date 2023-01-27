package org.akozlenko.gitstars.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.akozlenko.gitstars.utils.DispatcherProvider

class MainViewModel(
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    fun loadPageContent() {
        viewModelScope.launch {
            try {
                // TODO
            } catch (e: Exception) {
                // TODO
            }
        }
    }

}
