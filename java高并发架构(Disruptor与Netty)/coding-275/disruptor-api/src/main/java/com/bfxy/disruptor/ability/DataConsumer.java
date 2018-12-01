package com.bfxy.disruptor.ability;

import java.util.concurrent.ExecutorService;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;

public class DataConsumer implements EventHandler<Data> {

    private long startTime;
    private int i;
    public DataConsumer() {
        this.startTime = System.currentTimeMillis();
    }


	public DataConsumer(String string) {
		// TODO Auto-generated constructor stub
	}


	public void onEvent(Data data, long seq, boolean bool)
            throws Exception {
        i++;
        if (i == Constants.EVENT_NUM_OHM) {
            long endTime = System.currentTimeMillis();
            System.out.println("Disruptor costTime = " + (endTime - startTime) + "ms");//7500ms
           
        }
    }

}
