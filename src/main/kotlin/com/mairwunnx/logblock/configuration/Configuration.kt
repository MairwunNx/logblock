package com.mairwunnx.logblock.configuration

import kotlinx.serialization.Serializable

@Serializable
data class Configuration(
    val useEventLogger: Boolean = true,
    val printStartMessage: Boolean = true,
    val emptySlotName: String = "hand",
    val purgeLogDir: Boolean = true,
    val maxLoggerFiles: Int = 20,
    val dateTimeLogPattern: String = "ddMMMyyyy HH:mm:ss.SSS",
    val logFormatCapitalizeNames: Boolean = true,
    val prettyLoggerOut: Boolean = false,
    val prettyFormatting: PrettyFormatting = PrettyFormatting()
) {
    @Serializable
    data class PrettyFormatting(
        val worldEventSpying: Boolean = true,
        val didViaSpying: Boolean = true,
        val atEventPositionSpying: Boolean = true,
        val eventCauserSpying: Boolean = true,
        val eventCauserPositionSpying: Boolean = true,
        val eventWorldSpying: Boolean = true,
        val eventWorldIdSpying: Boolean = true,
        val eventWorldNameSpying: Boolean = true,
        val causerIpSpying: Boolean = true,
        val shortPositionVariant: Boolean = false
    )
}
