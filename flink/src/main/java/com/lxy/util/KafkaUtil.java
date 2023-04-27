package com.lxy.util;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class KafkaUtil {


    public static KafkaSource<String> getKafkaSource() {

        Properties kafkaProperties = new Properties();
        String bootstrapServers = null;
        String topics = null;
        String groupId = null;
        try {
            FileInputStream in = new FileInputStream("src/main/resources/kafka.properties");
            kafkaProperties.load(in);
            bootstrapServers = kafkaProperties.getProperty("bootstrapServers");
            topics = kafkaProperties.getProperty("topics");
            groupId = kafkaProperties.getProperty("groupId");
        } catch (Exception e) {
            e.printStackTrace();
        }
        KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                .setBootstrapServers(bootstrapServers)
                .setTopics(topics)
                .setGroupId(groupId)
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .setStartingOffsets(OffsetsInitializer.earliest())
                .build();

        return kafkaSource;
    }
}
