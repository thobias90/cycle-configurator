package com.stahlt.cycleconfigurator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.stahlt.cycleconfigurator.ui.AppCycleConfigurator
import com.stahlt.cycleconfigurator.ui.cycle.list.CycleListScreen
import com.stahlt.cycleconfigurator.ui.theme.CycleConfiguratorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CycleConfiguratorTheme {
                AppCycleConfigurator()
            }
        }
    }
}