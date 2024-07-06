package com.stahlt.cycleconfigurator.ui.cycle.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.stahlt.cycleconfigurator.ui.theme.CycleConfiguratorTheme

@Composable
fun CycleListScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize() ,
        topBar = {
            CyclesTopBar(modifier)
        } ,
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Add ,contentDescription = "Add")
            }
        }
    ) { paddingValues->
        Box(modifier = modifier.padding(paddingValues)) // just to not break
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CyclesTopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier ,
        title = { Text("Cycles") } ,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer ,
            titleContentColor = MaterialTheme.colorScheme.primary
        ) ,
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Refresh ,contentDescription = "Update")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CyclesTopBarPreview(modifier: Modifier = Modifier) {
    CycleConfiguratorTheme {
        CyclesTopBar()
    }
}