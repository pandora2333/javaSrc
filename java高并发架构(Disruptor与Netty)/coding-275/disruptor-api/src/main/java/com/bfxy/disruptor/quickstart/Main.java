package com.bfxy.disruptor.quickstart;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.TimeoutException;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class Main {

	
	
	public static void main(String[] args) {
		
		
		// 参数准备工作
		OrderEventFactory orderEventFactory = new OrderEventFactory();
		int ringBufferSize = 4;
		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		/**
		 * 1 eventFactory: 消息(event)工厂对象
		 * 2 ringBufferSize: 容器的长度
		 * 3 executor: 线程池(建议使用自定义线程池) RejectedExecutionHandler
		 * 4 ProducerType: 单生产者 还是 多生产者
		 * 5 waitStrategy: 等待策略
		 */
		//1. 实例化disruptor对象
		Disruptor<OrderEvent> disruptor = new Disruptor<OrderEvent>(orderEventFactory,
				ringBufferSize,
				executor,
				ProducerType.SINGLE,
				new BlockingWaitStrategy());
		
		//2. 添加消费者的监听 (构建disruptor 与 消费者的一个关联关系)
		disruptor.handleEventsWith(new OrderEventHandler());
		
		//3. 启动disruptor
		disruptor.start();
		
		//4. 获取实际存储数据的容器: RingBuffer
		RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();
		
		OrderEventProducer producer = new OrderEventProducer(ringBuffer);
		
		ByteBuffer bb = ByteBuffer.allocate(8);
		
		for(long i = 0 ; i < 5; i ++){
			System.out.println("work over!:"+i);
			bb.putLong(0, i);
			producer.sendData(bb);
		}
		System.out.println("work over!");
		try {
			disruptor.shutdown(2, TimeUnit.SECONDS);
			executor.shutdown();//关闭 disruptor 使用的线程池；如果需要的话，必须手动关闭， disruptor 在 shutdown 时不会自动关闭；
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};//关闭 disruptor，方法会堵塞，直至所有的事件都得到处理；
		
		
		
		
	}
}
