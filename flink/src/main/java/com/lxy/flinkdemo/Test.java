package com.lxy.flinkdemo;

import com.lxy.util.KafkaUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Test {
    public static void main(String[] args) throws IOException {
        Properties kafkaProperties = new Properties();

        FileInputStream in = new FileInputStream("src/main/resources/kafka.properties");
        kafkaProperties.load(in);
        String bootstrapServers = kafkaProperties.getProperty("bootstrapServers");
        String topics = kafkaProperties.getProperty("topics");
        String groupId = kafkaProperties.getProperty("groupId");
        System.out.println(bootstrapServers);
    }

}
