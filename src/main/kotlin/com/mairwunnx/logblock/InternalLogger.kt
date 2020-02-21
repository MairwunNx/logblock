package com.mairwunnx.logblock

import com.mairwunnx.logblock.metadata.PlayerMetaData
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

private val separator = File.separatorChar
private val logDirectory = "logs${separator}logblock"
private const val latestLog = "latest.log"
private lateinit var onLogMessage: (String) -> Unit
private lateinit var onLogClose: () -> Unit

fun init() {
    val files = File(logDirectory).listFiles() ?: emptyArray()

    purgeLogDir(files)
    doRolling(files)

    File(logDirectory + separator + latestLog).createNewFile()

    var writer: BufferedWriter? = null
    fun write(message: String) {
        writer = BufferedWriter(
            FileWriter(logDirectory + separator + latestLog, true)
        )
        writer!!.append(message)
        writer!!.newLine()
        writer!!.flush()
    }
    onLogMessage = ::write
    onLogClose = { writer?.close() }
}

private fun purgeLogDir(files: Array<File>) {
    files.forEach {
        if (it.isDirectory || it.extension != "log") {
            it.delete()
        }
    }
}

private fun doRolling(files: Array<File>) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")
    val currentDateTime = dateFormat.format(Date())

    files.forEach {
        if (it.isFile && !it.isDirectory) {
            if (it.name != latestLog.dropLast(it.extension.count())) {
                it.renameTo(
                    File(
                        it.path.replace(
                            "latest.log",
                            "latest-${currentDateTime}.log"
                        )
                    )
                )
            }
        }
    }

    if (files.count() > 20 - 1) {
        val lastModifiedDates = mutableListOf<Long>()
        files.forEach {
            if (it.isFile && !it.isDirectory) {
                lastModifiedDates.add(it.lastModified())
            }
        }

        val lastModified = lastModifiedDates.min()
        if (lastModified != null) {
            files.forEach {
                if (it.isFile && !it.isDirectory) {
                    if (it.lastModified() == lastModified) {
                        it.delete()
                    }
                }
            }
        }
    }
}

fun log(data: PlayerMetaData) = onLogMessage.invoke(format(data))

private fun format(data: PlayerMetaData): String {
    val dateFormat = SimpleDateFormat("ddMMMyyyy HH:mm:ss.SSS")
    val currentDateTime = dateFormat.format(Date())
    return "[# $currentDateTime] ${data.format()}"
}

fun close() = onLogClose.invoke()
