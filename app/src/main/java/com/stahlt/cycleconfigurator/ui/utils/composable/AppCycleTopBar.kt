package com.stahlt.cycleconfigurator.ui.utils.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.stahlt.cycleconfigurator.ui.theme.CycleConfiguratorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCycleTopBar(
    modifier: Modifier = Modifier,
    title: String,
    navigationIcon: Boolean = false,
    saveIcon: Boolean = false,
    refreshIcon: Boolean = false,
    deleteIcon: Boolean = false,
    onBackPressed: () -> Unit,
    onRefreshPressed: () -> Unit,
    onSavePressed: () -> Unit,
    onDeletePressed: () -> Unit
) {
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            if (navigationIcon) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack ,
                        contentDescription = "back"
                    )
                }
            }
        },
        actions = {
            if (deleteIcon) {
                IconButton(onClick = onDeletePressed) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "delete"
                    )
                }
            }
            if (saveIcon) {
                IconButton(onClick = onSavePressed) {
                    Icon(
                        imageVector = Icons.Filled.Check ,
                        contentDescription = "save"
                    )
                }
            }
            if (refreshIcon) {
                IconButton(onClick = onRefreshPressed) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "update"
                    )
                }
            }

        }
    )
}

@Preview(showBackground = true)
@Composable
fun AppCycleTopBarPreview(modifier: Modifier = Modifier) {
    CycleConfiguratorTheme {
        AppCycleTopBar(
            title = "TopBar",
            navigationIcon = true,
            refreshIcon = true,
            saveIcon = true,
            deleteIcon = true,
            onBackPressed = {},
            onSavePressed = {},
            onRefreshPressed = {},
            onDeletePressed = {}
        )
    }
}