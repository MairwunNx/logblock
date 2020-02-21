package com.mairwunnx.logblock.metadata

import com.mairwunnx.logblock.source.BreakSource
import com.mairwunnx.logblock.source.FireSource
import com.mairwunnx.logblock.source.PlaceSource
import com.mairwunnx.logblock.source.UseSource

sealed class WorldEventType

class BreakWorldEvent(val source: BreakSource) : WorldEventType() {
    override fun toString(): String = source.toString()
}

class PlaceWorldEvent(val source: PlaceSource) : WorldEventType() {
    override fun toString(): String = source.toString()
}

class UseWorldEvent(val source: UseSource) : WorldEventType() {
    override fun toString(): String = source.toString()
}

class FireWorldEvent(val source: FireSource) : WorldEventType() {
    override fun toString(): String = source.toString()
}
