package com.stahlt.cycleconfigurator.ui.cycle.list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stahlt.cycleconfigurator.data.cycle.Cycle
import com.stahlt.cycleconfigurator.data.cycle.network.ApiCycles
import kotlinx.coroutines.launch

data class CyclesListUIState(
    val loading: Boolean = false ,
    val hasError: Boolean = false ,
    val cycles: List<Cycle> = listOf()
) {
    val success get():Boolean = !loading && !hasError
}

class CyclesListViewModel: ViewModel() {
    private val tag: String = "CyclesListViewModel"
    var uiState by mutableStateOf(CyclesListUIState())

    init {
        load()
    }

    fun load() {
        uiState = uiState.copy(
            loading = true,
            hasError = false
        )
        viewModelScope.launch {
            uiState = try {
                val cycles = ApiCycles.retrofitService.findAll()
                uiState.copy(
                    loading = false,
                    cycles = cycles
                )
            } catch (ex: Exception) {
                Log.d(tag, "Error on Loading Cycles List", ex)
                uiState.copy(
                    hasError = true,
                    loading = false
                )
            }
        }
    }
}