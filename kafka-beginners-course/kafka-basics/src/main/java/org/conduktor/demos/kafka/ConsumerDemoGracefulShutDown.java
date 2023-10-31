package org.conduktor.demos.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemoGracefulShutDown {
    private static final Logger log = LoggerFactory.getLogger(ConsumerDemoGracefulShutDown.class.getName());
    public static void main(String[] args)  {
        log.info("I am consumer Demo");
        String groupId = "my-java-application";

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        properties.setProperty("group.id", groupId);
        properties.setProperty("auto.offset.reset", "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        String topic = "demo_java";

        final Thread mainThread = Thread.currentThread();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("wake up");
            consumer.wakeup();

            try {
                mainThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));



        try {
            consumer.subscribe(Arrays.asList(topic));

            while (true) {
                log.info("Polling");

                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

                for (ConsumerRecord<String, String> record: records) {
                    log.info("Key: " + record.key() + "\n" +
                            "value: " + record.value() + "\n" +
                            "partition: " + record.partition() + "\n" +
                            "offset: " + record.partition() );
                }
            }
        } catch (WakeupException exception) {
            log.info("Consumer is starting to shut down");
        } catch (Exception exception) {
            log.error("Unexpected Exception" + exception);
        } finally {
            log.info("the consumer is gracefully shut down");
            consumer.close();
        }
    }
}
