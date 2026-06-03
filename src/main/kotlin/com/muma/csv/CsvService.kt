package com.muma.csv

import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class CsvService {

    fun logTitles(file: MultipartFile) {
        val reader = file.inputStream.bufferedReader()
        val headers = reader.readLine()
            ?.split(",")
            ?.map { it.trim() }
            ?: return

        val titleIndex = headers.indexOf("title")
        if (titleIndex == -1) {
            logger.warn { "CSV에 title 컬럼이 없습니다." }
            return
        }

        reader.forEachLine { line ->
            val value = line.split(",").getOrNull(titleIndex)?.trim()
            logger.info { "title: $value" }
        }
    }

    companion object : KLogging()
}
