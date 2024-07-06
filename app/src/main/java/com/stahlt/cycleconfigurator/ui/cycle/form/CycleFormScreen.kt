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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun CycleFormScreen(modifier: Modifier = Modifier, onBackPressed: () -> Unit) {
    val isNewCycle = true
    Scaffold(
        topBar = {
            AppCycleTopBar(
                title = if (isNewCycle) "Add Cycle" else "Update Cycle",
                navigationIcon = true,
                saveIcon = true,
                onBackPressed = onBackPressed,
                onRefreshPressed = {},
                onSavePressed = {}
            )
        }
    ) { paddingValues ->
        FormContent(modifier = modifier.padding(paddingValues))
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
fun FormContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        CycleId(id = 1)
        FormTextField(
            label = "Name",
            value = "",
            onValueChanged = {},
            errorMessageCode = null,
            keyboardCapitalization = KeyboardCapitalization.Words
        )
        FormTextField(
            label = "Delay",
            value = "",
            onValueChanged = {},
            errorMessageCode = null,
            keyboardType = KeyboardType.Number
        )
        FormTextField(
            label = "Temperature",
            value = "",
            onValueChanged = {},
            errorMessageCode = null,
            keyboardCapitalization = KeyboardCapitalization.Words
        )
        FormTextField(
            label = "Spin Speed",
            value = "",
            onValueChanged = {},
            errorMessageCode = null,
            keyboardCapitalization = KeyboardCapitalization.Words
        )
        FormTextField(
            label = "Soil Level",
            value = "",
            onValueChanged = {},
            errorMessageCode = null,
            keyboardCapitalization = KeyboardCapitalization.Words,
            keyboardImeAction = ImeAction.Done
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FormContentPreview(modifier: Modifier = Modifier) {
    CycleConfiguratorTheme {
        FormContent()
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