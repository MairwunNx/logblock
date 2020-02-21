@file:Suppress("DuplicatedCode")

package com.mairwunnx.logblock

import com.mairwunnx.logblock.configuration.getSettings
import com.mairwunnx.logblock.configuration.loadSettings
import com.mairwunnx.logblock.configuration.saveSettings
import com.mairwunnx.logblock.metadata.*
import com.mairwunnx.logblock.source.BreakSource
import com.mairwunnx.logblock.source.FireSource
import com.mairwunnx.logblock.source.PlaceSource
import com.mairwunnx.logblock.source.UseSource
import net.minecraft.block.Blocks
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.Items
import net.minecraft.util.Hand
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent
import org.apache.logging.log4j.LogManager

@Mod("logblock")
class EntryPoint {
    private val logger = LogManager.getLogger()

    init {
        if (getSettings().printStartMessage) printStartupMessage()
        MinecraftForge.EVENT_BUS.register(this)
        loadSettings()
        if (getSettings().useEventLogger) initLogger()
    }

    private fun printStartupMessage() = logger.info(
        "\n\n-----------------------< LogBlock by MairwunNx >-----------------------\n\n".plus(
            "   keep track of almost everything on your server.\n\n"
        ).plus(
            "   LogBlock mod version: 1.0.0\n"
        ).plus(
            "   Target Minecraft mod loader: Forge\n"
        ).plus(
            "   Forge and Minecraft target version: 28.1.X & 1.14.4\n\n"
        ).plus(
            "   Source code: https://github.com/MairwunNx/logblock\n"
        ).plus(
            "   CurseForge: https://www.curseforge.com/minecraft/mc-mods/logblock\n"
        )
    )

    @SubscribeEvent
    fun onServerShutdown(event: FMLServerStoppingEvent) {
        closeLogger()
        saveSettings()
    }

    @SubscribeEvent
    fun onItemUse(event: LivingEntityUseItemEvent.Start) {
        if (event.entity is ServerPlayerEntity) {
            val player = event.entity as ServerPlayerEntity
            val ip = player.playerIP
            val name = player.name.string
            val pos = Position(player.posX, player.posY, player.posZ)
            val world = WorldInfo(player.dimension.id, player.dimension.registryName.toString())
            val usedItem = event.item.item
            val worldEvent =
                if (usedItem == Items.FLINT_AND_STEEL || usedItem == Items.FIRE_CHARGE) {
                    FireWorldEvent(FireSource(pos, usedItem.name.string))
                } else {
                    UseWorldEvent(UseSource(usedItem.name.string))
                }

            log(PlayerMetaData(ip, name, pos, world, worldEvent))
        }
    }

    @SubscribeEvent
    fun onBlockPlace(event: BlockEvent.EntityPlaceEvent) {
        if (event.entity is ServerPlayerEntity) {
            val player = event.entity as ServerPlayerEntity
            val ip = player.playerIP
            val name = player.name.string
            val pos = Position(player.posX, player.posY, player.posZ)
            val world = WorldInfo(player.dimension.id, player.dimension.registryName.toString())
            val worldEvent =
                if (event.placedBlock.block == Blocks.FIRE) {
                    FireWorldEvent(FireSource(pos, getCurrentItemName(player)))
                } else {
                    PlaceWorldEvent(
                        PlaceSource(
                            Position(
                                event.pos.x.toDouble(),
                                event.pos.y.toDouble(),
                                event.pos.z.toDouble()
                            ),
                            event.placedBlock.block.registryName.toString()
                        )
                    )
                }

            log(PlayerMetaData(ip, name, pos, world, worldEvent))
        }
    }

    @SubscribeEvent
    fun onBlockBreakEvent(event: BlockEvent.BreakEvent) {
        val player = event.player as ServerPlayerEntity
        val ip = player.playerIP
        val name = player.name.string
        val pos = Position(player.posX, player.posY, player.posZ)
        val world = WorldInfo(player.dimension.id, player.dimension.registryName.toString())
        val worldEvent = BreakWorldEvent(
            BreakSource(
                Position(
                    event.pos.x.toDouble(), event.pos.y.toDouble(), event.pos.z.toDouble()
                ),
                getCurrentItemName(player),
                event.world.getBlockState(event.pos).block.registryName.toString()
            )
        )

        log(PlayerMetaData(ip, name, pos, world, worldEvent))
    }

    private fun getCurrentItemName(player: ServerPlayerEntity): String =
        if (!player.getHeldItem(Hand.MAIN_HAND).isEmpty) {
            player.getHeldItem(Hand.MAIN_HAND).item.name.string
        } else {
            getSettings().emptySlotName
        }
}
