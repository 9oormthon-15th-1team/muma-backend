package com.muma.csv

import com.muma.common.response.ApiResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/csv")
class CsvController(
    private val csvService: CsvService,
) {

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun upload(
        @RequestPart file: MultipartFile,
    ): ApiResponse<Unit> {
        csvService.logTitles(file)
        return ApiResponse.success()
    }
}
