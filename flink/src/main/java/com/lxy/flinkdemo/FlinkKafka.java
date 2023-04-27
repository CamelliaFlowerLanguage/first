package com.lxy.flinkdemo;

import com.lxy.util.KafkaUtil;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FlinkKafka {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> kafka_source = env.fromSource(KafkaUtil.getKafkaSource(), WatermarkStrategy.noWatermarks(), "kafka_source");
        kafka_source.print();
        env.execute();
    }
}
