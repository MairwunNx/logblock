package com.mairwunnx.logblock.configuration

import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.apache.logging.log4j.LogManager
import java.io.File

private const val CONFIG_FOLDER = "config"
private val path = CONFIG_FOLDER + File.separator + "logblock.json"

private var configuration = Configuration()
private val logger = LogManager.getLogger()

@UseExperimental(UnstableDefault::class)
private val jsonInstance = Json(
    JsonConfiguration(
        strictMode = false,
        allowStructuredMapKeys = true,
        prettyPrint = true
    )
)

fun loadSettings() {
    logger.info("Loading logblock configuration")

    File(path).createNewFile()
    configuration = jsonInstance.parse(
        Configuration.serializer(), File(path).readText().let {
            if (it.isEmpty()) {
                logger.warn("Logblock config not exist! Using default settings!")
                return@let jsonInstance.stringify(
                    Configuration.serializer(), configuration
                )
            }
            return@let it
        }
    )
}

fun saveSettings() {
    logger.info("Saving logblock configuration")

    val configurationString = jsonInstance.stringify(
        Configuration.serializer(), configuration
    )
    File(path).writeText(configurationString)
}

fun getSettings() = configuration
