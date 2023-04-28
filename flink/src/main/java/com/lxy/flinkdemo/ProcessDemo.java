package com.lxy.flinkdemo;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
/*
侧输出流
 */
public class ProcessDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<Integer> numbers = env.fromElements(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        OutputTag<Integer> o1 = new OutputTag<Integer>("除3余1") {
        };

        OutputTag<Integer> o2 = new OutputTag<Integer>("除3余2") {
        };


        SingleOutputStreamOperator<Integer> process = numbers.process(new ProcessFunction<Integer, Integer>() {
            @Override
            public void processElement(Integer value, Context ctx, Collector<Integer> out) throws Exception {
                if (value % 3 == 1) {
                    ctx.output(o1, value);
                } else if (value % 3 == 2) {
                    ctx.output(o2, value);
                } else {
                    out.collect(value);
                }
            }
        });
        process.print("整除");
        process.getSideOutput(o1).print(o1.getId());
        process.getSideOutput(o2).print(o2.getId());
        env.execute();
    }
}
