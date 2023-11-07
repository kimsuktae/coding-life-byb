package com.kafkaschema.demo.controller

import com.kafkaschema.demo.data.SecondKey
import com.kafkaschema.demo.data.SecondMessage
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController(
    private val kafkaTemplate: KafkaTemplate<SecondKey, SecondMessage>
) {

    @GetMapping
    fun hello(): String {
        var count = 0
        for (j: Int in 1 .. 3 ) {
            for (i: Int in 1..10) {
                val record = ProducerRecord(
                    TOPIC,
                    null,
                    SecondKey(i.toString()),
                    SecondMessage(
                        "good no.$i"
                    )
                )

                kafkaTemplate.send(record)
                count += 1
            }
        }

        return count.toString()
    }

    companion object {
        private const val TOPIC = "second.topic"
    }
}
