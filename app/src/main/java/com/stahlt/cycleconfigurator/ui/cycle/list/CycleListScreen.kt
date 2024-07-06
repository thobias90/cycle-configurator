package com.stahlt.cycleconfigurator.ui.cycle.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stahlt.cycleconfigurator.data.cycle.Cycle
import com.stahlt.cycleconfigurator.ui.theme.CycleConfiguratorTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stahlt.apppedidos.ui.utils.composables.DefaultErrorLoading
import com.stahlt.cycleconfigurator.ui.utils.composable.AppCycleTopBar
import com.stahlt.cycleconfigurator.ui.utils.composable.DefaultLoading

@Composable
fun CycleListScreen(
    modifier: Modifier = Modifier,
    viewModel: CyclesListViewModel = viewModel(),
    onAddPressed: () -> Unit,
    onCyclePressed: (Cycle) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppCycleTopBar(
                title = "Cycles",
                refreshIcon = true,
                onRefreshPressed = viewModel::load,
                onBackPressed = {},
                onSavePressed = {}
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddPressed) {
                Icon(imageVector = Icons.Default.Add,contentDescription = "Add")
            }
        }
    ) { paddingValues->
        if (viewModel.uiState.loading) {
            DefaultLoading(
                modifier = modifier,
                text = "Loading"
            )
        } else if (viewModel.uiState.hasError) {
            DefaultErrorLoading(
                modifier = modifier,
                text = "Unable to load cycles",
                onTryAgainPressed = viewModel::load
            )
        } else {
            CyclesList(
                modifier = modifier.padding(paddingValues),
                cycles = viewModel.uiState.cycles,
                onCyclePressed = onCyclePressed
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CyclesTopBarPreview(modifier: Modifier = Modifier) {
    CycleConfiguratorTheme {
        AppCycleTopBar(
            title = "Cycles",
            refreshIcon = true,
            onRefreshPressed = {},
            onSavePressed = {},
            onBackPressed = {}
        )
    }
}

@Composable
fun CyclesList(
    modifier: Modifier = Modifier,
    cycles: List<Cycle>,
    onCyclePressed: (Cycle) -> Unit
) {
    if (cycles.isEmpty()) {
        EmptyList(modifier)
    } else {
        FilledList(
            modifier,
            cycles,
            onCyclePressed = onCyclePressed
        )
    }
}

@Composable
fun EmptyList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "You don't have any cycles",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Add cycles pressing \"+\"",
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true,heightDp = 400)
@Composable
fun EmptyListPreview(modifier: Modifier = Modifier) {
    CycleConfiguratorTheme {
        EmptyList(modifier)
    }
}

@Composable
fun FilledList(modifier: Modifier = Modifier,cycles: List<Cycle>,onCyclePressed: (Cycle) -> Unit) {
    LazyColumn(modifier = modifier) {
        items(cycles) { cycle->
            ListItem(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .clickable { onCyclePressed(cycle) },
                headlineContent = {
                    Text(
                        text = "${cycle.id} - ${cycle.name}",
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Select"
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilledListPreview(modifier: Modifier = Modifier) {
    CycleConfiguratorTheme {
        FilledList(modifier = modifier,cycles = cyclesFake,onCyclePressed = {})
    }
}

val cyclesFake: List<Cycle> = listOf(
    Cycle(
        id = 1,
        name = "Normal",
        delay = 20,
        temperature = "Hot",
        spinSpeed = "Medium",
        soilLevel = "Medium"
    ),
    Cycle(
        id = 2,
        name = "Quick",
        delay = 0,
        temperature = "Cold",
        spinSpeed = "Slow",
        soilLevel = "Low"
    ),
)