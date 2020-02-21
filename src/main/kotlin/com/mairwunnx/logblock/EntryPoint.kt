package com.mairwunnx.logblock

import com.mairwunnx.logblock.metadata.BreakWorldEvent
import com.mairwunnx.logblock.metadata.PlayerMetaData
import com.mairwunnx.logblock.metadata.Position
import com.mairwunnx.logblock.metadata.WorldInfo
import com.mairwunnx.logblock.source.BreakSource
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent
import org.apache.logging.log4j.LogManager
import kotlin.system.measureTimeMillis

@Mod("logblock")
class EntryPoint {
    private val logger = LogManager.getLogger()

    init {
        printStartupMessage()
        init()

        log(
            PlayerMetaData(
                "0", "dasd", Position(0.0, 0.0, 0.0), WorldInfo(
                    0, "0"
                ), BreakWorldEvent(
                    BreakSource(
                        Position(0.0, 0.0, 0.0), "dsd", "qqq"
                    )
                )
            )
        )

        log(
            PlayerMetaData(
                "asdsad", "asddwq", Position(0.0, 0.0, 0.0), WorldInfo(
                    0, "0"
                ), BreakWorldEvent(
                    BreakSource(
                        Position(0.0, 0.0, 0.0), "dsd", "qqq"
                    )
                )
            )
        )
    }

    private fun printStartupMessage() = logger.info(
        "\n\n-----------------------< LogBlock by MairwunNx >-----------------------\n".plus(
            "       keep track of almost everything on your server.\n\n"
        ).plus(
            "       LogBlock mod version: 1.0.0\n"
        ).plus(
            "       Target Minecraft mod loader: Forge\n"
        ).plus(
            "       Forge and Minecraft target version: 28.1.X & 1.14.4\n\n"
        ).plus(
            "       Source code: https://github.com/MairwunNx/logblock\n"
        ).plus(
            "       CurseForge: https://www.curseforge.com/minecraft/mc-mods/logblock\n"
        )
    )

    @SubscribeEvent
    fun onServerShutdown(event: FMLServerStoppingEvent) {
        close()
    }

    @SubscribeEvent
    fun onItemUse(event: LivingEntityUseItemEvent.Finish) {

    }

    @SubscribeEvent
    fun onBlockPlace(event: BlockEvent.EntityPlaceEvent) {

    }

    @SubscribeEvent
    fun onBlockBreakEvent(event: BlockEvent.BreakEvent) {

    }
}
