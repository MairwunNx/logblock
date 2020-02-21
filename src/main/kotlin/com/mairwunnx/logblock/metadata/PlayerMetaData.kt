@file:Suppress("DuplicatedCode")

package com.mairwunnx.logblock.metadata

import com.mairwunnx.logblock.configuration.getSettings
import java.text.DecimalFormat

private val decimalFormat = DecimalFormat("0.#")

fun Double.cut(): String {
    if (getSettings().prettyFormatting.shortPositionVariant) {
        return decimalFormat.format(this)
    }
    return this.toString()
}

data class PlayerMetaData(
    val ip: String,
    val playerName: String,
    val position: Position,
    val worldInfo: WorldInfo,
    val worldEvent: WorldEventType
) {
    fun format(): String {
        var result = this.toString()

        if (getSettings().logFormatCapitalizeNames) {
            result.forEachIndexed { index, char ->
                if (char == '=') {
                    val str = result.substring(0, index)
                    val newStr = str.takeLastWhile { lastChar ->
                        lastChar != '(' && lastChar != ' '
                    }
                    result = result.replace(newStr, newStr.capitalize())
                }
            }
        }

        return result
    }

    fun prettyFormat(): String {
        val builder = StringBuilder().apply {
            when (worldEvent) {
                is BreakWorldEvent -> {
                    if (getSettings().prettyFormatting.worldEventSpying) {
                        append("Breaked block (${worldEvent.source.breakedObject})")
                    }
                    if (getSettings().prettyFormatting.didViaSpying) {
                        append(" via (${worldEvent.source.breakVia})")
                    }
                    if (getSettings().prettyFormatting.atEventPositionSpying) {
                        append(" at (XYZ: ${worldEvent.source.breakPosition.xPos}, ${worldEvent.source.breakPosition.yPos}, ${worldEvent.source.breakPosition.zPos})")
                    }
                }
                is PlaceWorldEvent -> {
                    if (getSettings().prettyFormatting.worldEventSpying) {
                        append("Placed block (${worldEvent.source.placedObject})")
                    }
                    if (getSettings().prettyFormatting.atEventPositionSpying) {
                        append(" at (XYZ: ${worldEvent.source.placePosition.xPos}, ${worldEvent.source.placePosition.yPos}, ${worldEvent.source.placePosition.zPos})")
                    }
                }
                is UseWorldEvent -> {
                    if (getSettings().prettyFormatting.worldEventSpying) {
                        append("Used item (${worldEvent.source.usedObject})")
                    }
                }
                is FireWorldEvent -> {
                    if (getSettings().prettyFormatting.worldEventSpying) {
                        append("Fired object")
                    }
                    if (getSettings().prettyFormatting.didViaSpying) {
                        append(" via (${worldEvent.source.firedVia})")
                    }
                    if (getSettings().prettyFormatting.atEventPositionSpying) {
                        append(" at (XYZ: ${worldEvent.source.firePosition.xPos}, ${worldEvent.source.firePosition.yPos}, ${worldEvent.source.firePosition.zPos})")
                    }
                }
            }

            if (getSettings().prettyFormatting.eventCauserSpying) {
                append(" by $playerName")
                if (getSettings().prettyFormatting.eventCauserPositionSpying) {
                    append(" at (XYZ: ${position.xPos.cut()}, ${position.yPos.cut()}, ${position.zPos.cut()})")
                }
            }

            if (getSettings().prettyFormatting.eventWorldSpying) {
                var idEnabled = false
                if (getSettings().prettyFormatting.eventWorldIdSpying) {
                    idEnabled = true
                    append(" in world (id: ${worldInfo.worldId}")
                }
                if (getSettings().prettyFormatting.eventWorldNameSpying) {
                    if (idEnabled) {
                        append(", name: ${worldInfo.worldName})")
                    } else {
                        append(" in world: (name: ${worldInfo.worldName})")
                    }
                }
            }

            if (getSettings().prettyFormatting.causerIpSpying) {
                append(" causer ip ($ip)")
            }
        }
        return builder.toString()
    }
}
