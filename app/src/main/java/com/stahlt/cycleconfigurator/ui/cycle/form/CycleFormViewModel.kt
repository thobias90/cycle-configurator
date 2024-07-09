package com.stahlt.cycleconfigurator.ui.cycle.form

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stahlt.cycleconfigurator.data.cycle.Cycle
import com.stahlt.cycleconfigurator.data.cycle.network.ApiCycles
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class FormState(
    var name: String = "",
    var delay: Int = 0,
    var temperature: String = "",
    var spinSpeed: String = "",
    var soilLevel: String = ""
)

data class CycleFormUiState(
    val cycleId: Int = 0,
    val formState: FormState = FormState(),
    val isLoading: Boolean = false,
    val hasErrorLoading: Boolean = false,
    val isSaving: Boolean = false,
    val hasErrorSaving: Boolean = false,
    val saveCompleted: Boolean = false,
    val isDeleting: Boolean = false,
    val hasErrorDeleting: Boolean = false,
    val deleteCompleted: Boolean = false,
    val showConfirmationDialog: Boolean = false
) {
    val isNewCycle get(): Boolean = cycleId <= 0
    val isSuccessLoading get(): Boolean = !isLoading && !hasErrorLoading
}

class CycleFormViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val tag: String = "CycleFormViewModel"
    private val cycleId: Int = savedStateHandle.get<String>("id")?.toIntOrNull() ?: 0

    var uiState: CycleFormUiState by mutableStateOf(CycleFormUiState())

    init {
        if (cycleId > 0) {
            load()
        }
    }



    fun onNameChanged(value: String) {
        if (uiState.formState.name != value) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    name = value
                )
            )
        }
    }

    fun onDelayChanged(value: String) {
        if (value.isNotEmpty() && value.isDigitsOnly()) {
            if (uiState.formState.delay != value.toInt()) {
                uiState = uiState.copy(
                    formState = uiState.formState.copy(
                        delay = value.toInt()
                    )
                )
            }
        }
    }

    fun onTemperatureChanged(value: String) {
        if (uiState.formState.temperature != value) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    temperature = value
                )
            )
        }
    }

    fun onSpinSpeedChanged(value: String) {
        if (uiState.formState.spinSpeed != value) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    spinSpeed = value
                )
            )
        }
    }

    fun onSoilLevelChanged(value: String) {
        if (uiState.formState.soilLevel != value) {
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    soilLevel = value
                )
            )
        }
    }

    fun load() {
        uiState = uiState.copy(
            isLoading = true,
            hasErrorLoading = false,
            cycleId = cycleId
        )
        viewModelScope.launch {
            uiState = try {
                val cycle = ApiCycles.retrofitService.findById(cycleId)
                uiState.copy(
                    isLoading = false,
                    formState = FormState(
                        name = cycle.name,
                        delay = cycle.delay,
                        temperature = cycle.temperature,
                        spinSpeed = cycle.spinSpeed,
                        soilLevel = cycle.soilLevel
                    )
                )
            } catch(e: Exception) {
                Log.d(tag, "Error loading findbyid ${cycleId}, e: ",e)
                uiState.copy(isLoading = false, hasErrorLoading = true)
            }
        }
    }
    fun save() {
        uiState = uiState.copy(
            isSaving = true,
            hasErrorSaving = false
        )
        viewModelScope.launch {
            delay(500)
            val cycle = Cycle(
                id = cycleId,
                name = uiState.formState.name,
                delay = uiState.formState.delay,
                temperature = uiState.formState.temperature,
                spinSpeed = uiState.formState.spinSpeed,
                soilLevel = uiState.formState.soilLevel
            )
            uiState = try {
                val response = ApiCycles.retrofitService.save(cycle)
                if (response.isSuccessful) {
                    uiState.copy(
                        isSaving = false,
                        saveCompleted = true
                    )
                } else {
                    uiState.copy(
                        isSaving = false,
                        hasErrorSaving = true
                    )
                }
            } catch (ex: Exception) {
                Log.d(tag, "Error loading cycle id $cycleId ex: ", ex)
                uiState.copy(
                    isSaving = false,
                    hasErrorSaving = true
                )
            }
        }
    }
    fun delete() {
        uiState = uiState.copy(
            isDeleting = true,
            hasErrorDeleting = false,
            showConfirmationDialog = false
        )
        viewModelScope.launch {
            delay(500)
            uiState = try {
                ApiCycles.retrofitService.delete(cycleId)
                uiState.copy(
                    isDeleting = false,
                    deleteCompleted = true
                )
            } catch (ex: Exception) {
                Log.d(tag, "Error deleting cycle ${cycleId} ex: ", ex)
                uiState.copy(
                    hasErrorDeleting = true,
                    isDeleting = false
                )
            }
        }
    }

    fun showConfirmationDialog() {
        uiState = uiState.copy(
            showConfirmationDialog = true
        )
    }

    fun dismissConfirmationDialog() {
        uiState = uiState.copy(
            showConfirmationDialog = false
        )
    }
}