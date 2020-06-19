package com.marivan

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PaymentsManagerApplication

fun main(args: Array<String>) {
	runApplication<PaymentsManagerApplication>(*args)
}
