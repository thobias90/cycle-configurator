package com.stahlt.cycleconfigurator.ui.cycle.form

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.stahlt.cycleconfigurator.ui.theme.CycleConfiguratorTheme

@Composable
fun CycleFormScreen(modifier: Modifier = Modifier, onBackPressed: () -> Unit) {
    Box {
        IconButton(onClick = onBackPressed) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CycleFormScreenPreview(modifier: Modifier = Modifier) {
    CycleConfiguratorTheme {
        CycleFormScreen(onBackPressed = {})
    }
}