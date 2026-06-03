package com.muma

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.retry.annotation.EnableRetry

@EnableRetry
@SpringBootApplication
class MumaBackendApplication

fun main(args: Array<String>) {
	runApplication<MumaBackendApplication>(*args)
}
