package com.mairwunnx.logblock.configuration

import kotlinx.serialization.Serializable

@Serializable
data class Configuration(
    val useEventLogger: Boolean = true
)
