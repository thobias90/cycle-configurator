package com.stahlt.apppedidos.ui.utils.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stahlt.cycleconfigurator.ui.theme.CycleConfiguratorTheme

@Composable
fun DefaultErrorLoading(
    modifier: Modifier = Modifier ,
    text: String ,
    onTryAgainPressed: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize() ,
        verticalArrangement = Arrangement.Center ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.CloudOff ,
            contentDescription = "cloudOff" ,
            tint = MaterialTheme.colorScheme.primary ,
            modifier = Modifier.size(80.dp)
        )
        Text(
            modifier = Modifier.padding(top = 8.dp ,start = 8.dp ,end = 8.dp) ,
            text = text ,
            style = MaterialTheme.typography.titleLarge ,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            modifier = Modifier.padding(top = 8.dp ,start = 8.dp ,end = 8.dp) ,
            text = "Please wait a moment and try again." ,
            style = MaterialTheme.typography.titleSmall ,
            color = MaterialTheme.colorScheme.primary
        )
        ElevatedButton(
            onClick = onTryAgainPressed ,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Try Again")
        }
    }
}

@Preview(showBackground = true ,heightDp = 400)
@Composable
fun ErrorLoadingClientesPreview(modifier: Modifier = Modifier) {
    CycleConfiguratorTheme {
        DefaultErrorLoading(
            text = "Unable to load cycles"  ,
            onTryAgainPressed = {}
        )
    }
}