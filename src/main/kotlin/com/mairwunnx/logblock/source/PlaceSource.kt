package com.mairwunnx.logblock.source

import com.mairwunnx.logblock.metadata.Position

data class PlaceSource(
    val placePosition: Position,
    val placedObject: String
)

