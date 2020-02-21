package com.mairwunnx.logblock.source

import com.mairwunnx.logblock.metadata.Position

data class FireSource(
    val firePosition: Position,
    val firedVia: String
)

