package com.lxy.flinkdemo;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

public class KafkaSinkDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);

        DataStreamSource<String> socketTextStream = env.socketTextStream("localhost", 9999);
        Properties kafkaProperties = new Properties();
//        kafkaProperties.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 10 * 1024 * 1024);
        kafkaProperties.put(ProducerConfig.ACKS_CONFIG, "all");
        kafkaProperties.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, 50000);

        KafkaSink<String> kafkaSink = KafkaSink.<String>builder()
                .setBootstrapServers("cdh2:9092,cdh3:9092,cdh4:9092")
                .setRecordSerializer(
                        KafkaRecordSerializationSchema.builder()
                                .setTopic("test_kafka_topic")
                                .setKeySerializationSchema(new SimpleStringSchema())
                                .setValueSerializationSchema(new SimpleStringSchema())
                                .build()
                ).setDeliverGuarantee(DeliveryGuarantee.EXACTLY_ONCE)
                .setKafkaProducerConfig(kafkaProperties)
                .build();

        socketTextStream.sinkTo(kafkaSink);

        env.execute();
    }
}
