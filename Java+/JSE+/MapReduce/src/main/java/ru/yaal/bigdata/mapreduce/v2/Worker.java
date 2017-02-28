package ru.yaal.bigdata.mapreduce.v2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class Worker implements Callable<Result> {
	private BlockingQueue<String> queue;
	
	public Worker(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	@Override
	public Result call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
