package com.muma.csv

import mu.KLogging
import org.apache.commons.csv.CSVFormat
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class CsvService(
    private val eventPublisher: ApplicationEventPublisher,
) {

    fun logTitles(file: MultipartFile) {
        val format = CSVFormat.DEFAULT.builder()
            .setHeader()
            .setSkipHeaderRecord(true)
            .setTrim(true)
            .build()

        val records = format.parse(file.inputStream.bufferedReader())
        val headers = records.headerNames

        if ("title" !in headers) logger.warn { "CSV에 title 컬럼이 없습니다." }
        if ("artists" !in headers) logger.warn { "CSV에 artists 컬럼이 없습니다." }
        if ("title" !in headers && "artists" !in headers) return

        for (record in records) {
            val title = if ("title" in headers) record["title"] else null
            val artists = if ("artists" in headers) record["artists"] else null
            logger.info { "title: $title, artists: $artists" }

            if (title != null && artists != null) {
                eventPublisher.publishEvent(TrackInfoReceivedEvent(title = title, artists = artists))
            }
        }
    }

    companion object : KLogging()
}
