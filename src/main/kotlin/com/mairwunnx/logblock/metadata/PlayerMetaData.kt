package com.mairwunnx.logblock.metadata

data class PlayerMetaData(
    val ip: String,
    val playerName: String,
    val position: Position,
    val worldInfo: WorldInfo,
    val worldEvent: WorldEventType
) {
    fun format(): String {
        var result = this.toString()

        result.forEachIndexed { index, char ->
            if (char == '=') {
                val str = result.substring(0, index)
                val newStr = str.takeLastWhile { lastChar ->
                    lastChar != '(' && lastChar != ' '
                }
                result = result.replace(newStr, newStr.capitalize())
            }
        }
        return result
    }
}
