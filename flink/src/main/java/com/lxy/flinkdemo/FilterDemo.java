package com.lxy.flinkdemo;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FilterDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<Integer> numbers = env.fromElements(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        SingleOutputStreamOperator<Integer> filter = numbers.filter(new Myfilter());

        filter.print();

        env.execute();


    }

}
class Myfilter implements FilterFunction<Integer>{
    @Override
    public boolean filter(Integer value) throws Exception {
        return value % 2 == 0;
    }
}

