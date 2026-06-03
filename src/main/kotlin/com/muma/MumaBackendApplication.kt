package com.muma

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MumaBackendApplication

fun main(args: Array<String>) {
	runApplication<MumaBackendApplication>(*args)
}
