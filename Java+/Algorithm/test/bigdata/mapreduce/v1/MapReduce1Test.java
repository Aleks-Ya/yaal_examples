package bigdata.mapreduce.v1;

import bigdata.mapreduce.cyclic.CyclicReader;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MapReduce1Test {

	@Test
	public void test() throws IOException, InterruptedException {
		final int fileSize = 100_000_000;
		Reader file = new StringReader("Hello everybody! Common! 0123456789 ");
		Reader inputFile = new CyclicReader(file, fileSize);

		final int workersCount = 4;
		Master master = new MasterImpl();
		List<? extends Callable<Result>> jobs = master.map(inputFile, fileSize, workersCount);

		ExecutorService executor = Executors.newFixedThreadPool(workersCount);
		final List<Future<Result>> futures = executor.invokeAll(jobs);
		executor.shutdown();

		final Set<String> longestWords = master.reduce(futures);

		System.out.println("Longest words: " + longestWords);

		assertThat(longestWords, contains("everybody!", "0123456789"));
	}

}
