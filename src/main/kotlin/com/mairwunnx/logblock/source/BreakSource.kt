package com.mairwunnx.logblock.source

import com.mairwunnx.logblock.metadata.Position

data class BreakSource(
    val breakPosition: Position,
    val breakVia: String,
    val breakedObject: String
)
