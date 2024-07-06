package com.stahlt.cycleconfigurator.data.cycle

import kotlinx.serialization.Serializable

@Serializable
data class Cycle(
    var id: Int = 0,
    var name: String = "",
    var delay: Int = 0,
    var temperature: String = "",
    var spinSpeed: String = "",
    var soilLevel: String = ""
)
