package com.usktea.controller

import com.kafkaschema.demo.data.SecondKey
import com.kafkaschema.demo.data.SecondMessage
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class MessageController(
    private val kafkaTemplate: KafkaTemplate<SecondKey, SecondMessage>
) {

    @GetMapping
    fun hello(): String {
        val threads = mutableListOf<Thread>()

        for (i in 1..50) {
            val thread = Thread {
                sendMessage()
            }
            thread.start()
            threads.add(thread)
        }

        for (thread in threads) {
            thread.join()
        }

        println("ok")

        return "ok"
    }

    private fun sendMessage() {
        for (j: Int in 1..10) {
            for (i: Int in 1..1000) {
                val record = ProducerRecord(
                    TOPIC,
                    null,
                    SecondKey(i.toString()),
                    SecondMessage(
                        "good no.$i"
                    )
                )

                kafkaTemplate.send(record)
            }
        }
    }

    companion object {
        private const val TOPIC = "second.topic"
    }
}
