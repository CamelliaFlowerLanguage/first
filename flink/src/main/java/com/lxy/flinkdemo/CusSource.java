package com.lxy.flinkdemo;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

public class CusSource implements SourceFunction<String> {

    private int i = 1;
    private boolean flag = true;

    @Override
    public void run(SourceContext<String> ctx) throws Exception {
        while (i < 10 && flag) {
            Thread.sleep(2000);
            ctx.collect("data:" + i++);
        }

    }

    @Override
    public void cancel() {
        flag = false;
    }
}
