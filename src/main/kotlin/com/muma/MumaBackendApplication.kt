package com.muma

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
class MumaBackendApplication

fun main(args: Array<String>) {
	runApplication<MumaBackendApplication>(*args)
}
