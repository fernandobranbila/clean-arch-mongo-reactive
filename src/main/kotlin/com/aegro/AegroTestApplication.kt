package com.aegro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
class AegroTestApplication

fun main(args: Array<String>) {
	runApplication<AegroTestApplication>(*args)
}
