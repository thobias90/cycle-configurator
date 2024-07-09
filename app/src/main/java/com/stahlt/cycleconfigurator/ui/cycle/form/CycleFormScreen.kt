package com.stahlt.cycleconfigurator.ui.cycle.form

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stahlt.cycleconfigurator.ui.theme.CycleConfiguratorTheme
import com.stahlt.cycleconfigurator.ui.utils.composable.AppCycleTopBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stahlt.apppedidos.ui.utils.composables.DefaultErrorLoading
import com.stahlt.cycleconfigurator.ui.utils.composable.DefaultLoading

@Composable
fun CycleFormScreen(
    modifier: Modifier = Modifier,
    viewModel: CycleFormViewModel = viewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onBackPressed: () -> Unit,
    onCycleSaved: () -> Unit,
    onCycleDeleted: () -> Unit
) {
    LaunchedEffect(viewModel.uiState.saveCompleted) {
        if (viewModel.uiState.saveCompleted) {
            onCycleSaved()
        }
    }
    LaunchedEffect(viewModel.uiState.deleteCompleted) {
        if (viewModel.uiState.deleteCompleted) {
            onCycleDeleted()
        }
    }
    LaunchedEffect(snackbarHostState, viewModel.uiState.hasErrorDeleting) {
        if (viewModel.uiState.hasErrorDeleting) {
            snackbarHostState.showSnackbar("Error on deleting")
        }
    }
    LaunchedEffect(snackbarHostState, viewModel.uiState.hasErrorSaving) {
        if (viewModel.uiState.hasErrorSaving) {
            snackbarHostState.showSnackbar("Error on saving")
        }
    }
    if (viewModel.uiState.showConfirmationDialog) {
        ConfirmationDialog(
            title = "Attention",
            text = "This cycle will be removed and can't be recovered",
            onDismiss = viewModel::dismissConfirmationDialog,
            onConfirm = viewModel::delete
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            AppCycleTopBar(
                title = if (viewModel.uiState.cycleId <= 0) "Add Cycle" else "Update Cycle",
                navigationIcon = true,
                saveIcon = !viewModel.uiState.hasErrorLoading,
                deleteIcon = !viewModel.uiState.hasErrorLoading && (viewModel.uiState.cycleId > 0),
                onBackPressed = onBackPressed,
                onSavePressed = viewModel::save,
                onRefreshPressed = {},
                onDeletePressed = viewModel::showConfirmationDialog
            )
        }
    ) { paddingValues ->
        if (viewModel.uiState.isLoading) {
            DefaultLoading(text = "Loading")
        } else if (viewModel.uiState.hasErrorLoading) {
            DefaultErrorLoading(
                text = "Error Loading",
                onTryAgainPressed = viewModel::load
            )
        } else if (viewModel.uiState.isDeleting) {
            DefaultLoading(text = "Deleting")
        } else if (viewModel.uiState.isSaving) {
            DefaultLoading(text = "Saving")
        } else {
            FormContent(
                modifier = modifier.padding(paddingValues),
                cycleId = viewModel.uiState.cycleId,
                formState = viewModel.uiState.formState,
                onNameChanged = viewModel::onNameChanged,
                onDelayChanged = viewModel::onDelayChanged,
                onTemperatureChanged = viewModel::onTemperatureChanged,
                onSoilLevelChanged = viewModel::onSoilLevelChanged,
                onSpinSpeedChanged = viewModel::onSpinSpeedChanged
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun CycleFormScreenPreview(modifier: Modifier = Modifier) {
//    CycleConfiguratorTheme {
//        CycleFormScreen(onBackPressed = {})
//    }
//}

@Composable
fun FormContent(
    modifier: Modifier = Modifier,
    cycleId: Int,
    formState: FormState,
    onNameChanged: (String) -> Unit,
    onDelayChanged: (String) -> Unit,
    onTemperatureChanged: (String) -> Unit,
    onSpinSpeedChanged: (String) -> Unit,
    onSoilLevelChanged: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        CycleId(id = cycleId)
        FormTextField(
            label = "Name",
            value = formState.name,
            onValueChanged = onNameChanged,
            errorMessageCode = null,
            keyboardCapitalization = KeyboardCapitalization.Words
        )
        FormTextField(
            label = "Delay",
            value = formState.delay.toString(),
            onValueChanged = onDelayChanged,
            errorMessageCode = null,
            keyboardType = KeyboardType.Number
        )
        FormTextField(
            label = "Temperature",
            value = formState.temperature,
            onValueChanged = onTemperatureChanged,
            errorMessageCode = null,
            keyboardCapitalization = KeyboardCapitalization.Words
        )
        FormTextField(
            label = "Spin Speed",
            value = formState.spinSpeed,
            onValueChanged = onSpinSpeedChanged,
            errorMessageCode = null,
            keyboardCapitalization = KeyboardCapitalization.Words
        )
        FormTextField(
            label = "Soil Level",
            value = formState.soilLevel,
            onValueChanged = onSoilLevelChanged,
            errorMessageCode = null,
            keyboardCapitalization = KeyboardCapitalization.Words,
            keyboardImeAction = ImeAction.Done
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FormContentPreview() {
    CycleConfiguratorTheme {
        FormContent(
            cycleId = 1,
            formState = FormState(),
            onNameChanged = {},
            onDelayChanged = {},
            onTemperatureChanged = {},
            onSpinSpeedChanged = {},
            onSoilLevelChanged = {}
        )
    }
}
@Composable
private fun CycleId(
    modifier: Modifier = Modifier ,
    id: Int
) {
    if (id > 0) {
        FormTitle(text = "Id $id")
    } else {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FormTitle(text = "Code = ")
            Text(
                text = "to define",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontStyle = FontStyle.Italic
                )
            )
        }
    }
}
@Composable
private fun FormTitle(
    modifier: Modifier = Modifier ,
    text: String
) {
    Text(
        modifier = modifier.padding(start = 16.dp) ,
        text = text ,
        style = MaterialTheme.typography.titleLarge ,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun FormTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChanged: (String) -> Unit,
    enabled: Boolean = true,
    @StringRes
    errorMessageCode: Int? = null,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    keyboardImeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value ,
        onValueChange = onValueChanged,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp,horizontal = 16.dp),
        label = { Text(label) },
        maxLines = 1,
        enabled = enabled,
        isError = errorMessageCode != null,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            capitalization = keyboardCapitalization,
            imeAction = keyboardImeAction,
            keyboardType = keyboardType
        ),
        visualTransformation = visualTransformation
    )
    errorMessageCode?.let {
        Text(
            text = stringResource(id = errorMessageCode) ,
            color = MaterialTheme.colorScheme.error ,
            style = MaterialTheme.typography.labelSmall ,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun ConfirmationDialog(
    modifier: Modifier = Modifier,
    title: String? = null,
    text: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    dismissButtonText: String? = null,
    confirmButtonText: String? = null
) {
    AlertDialog(
        modifier = modifier,
        title = title?.let {
            { Text(it) }
        },
        text = { Text(text) },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(confirmButtonText ?: "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(dismissButtonText ?: "Dismiss")
            }
        }
    )
}