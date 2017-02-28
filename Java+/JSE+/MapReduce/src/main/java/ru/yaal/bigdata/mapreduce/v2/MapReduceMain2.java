package ru.yaal.bigdata.mapreduce.v2;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.yaal.bigdata.mapreduce.cyclic.CyclicReader;

public class MapReduceMain2 {

	public static void main(String[] args) throws IOException {
		final int fileSize = 100_000_000;
		Reader file = new StringReader("Hello everybody! Common! 0123456789 ");
		Reader inputFile = new CyclicReader(file, fileSize);

		BlockingQueue<String> queue = new ArrayBlockingQueue<>(4);

		int workersCount = 4;
		final ExecutorService pool = Executors.newFixedThreadPool(workersCount);
		for (int i = 0; i < workersCount; i++) {
			pool.submit(new Worker(queue));
		}
		
		
		
	}

}
