package com.kafkaschema.demo.listener

import com.kafkaschema.demo.data.SecondKey
import com.kafkaschema.demo.data.SecondMessage
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class MessageListener {

    @KafkaListener(topics = [TOPIC])
    fun getMessages(messages: List<ConsumerRecord<SecondKey, SecondMessage>>) {
        messages.forEach {
            println(it.key())
            println(it.key().messageId)
            println(it.value())
            println(it.value().message)
        }
    }

    companion object {
        private const val TOPIC = "second.topic"
    }
}
