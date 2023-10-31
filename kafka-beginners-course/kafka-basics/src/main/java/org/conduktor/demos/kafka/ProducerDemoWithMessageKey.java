package org.conduktor.demos.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithMessageKey {
    private static final Logger log = LoggerFactory.getLogger(ProducerDemoWithMessageKey.class.getName());

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);


        for (int i = 0; i < 2; i += 1) {
            for (int j = 0; j < 30; j += 1) {
                String topic = "demo_java";
                String key = "id_" + j;
                String value = "Hello world " + j;

                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, value);

                producer.send(producerRecord, (metadata, exception) -> {
                    if (exception == null) {
                        log.info("Received new metadata \n" +
                                "topic: " + metadata.topic() + "\n" +
                                " key:" + key + "\n" +
                                "partition: " + metadata.partition() + "\n" +
                                "offset: " + metadata.offset() + "\n" +
                                "timestamp:" + metadata.timestamp()
                        );

                        return;
                    }

                    log.error(exception.getMessage());
                });
            }
        }

        producer.flush();
        producer.close();
    }
}
