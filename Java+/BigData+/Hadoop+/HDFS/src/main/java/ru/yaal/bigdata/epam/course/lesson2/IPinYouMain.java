package ru.yaal.bigdata.epam.course.lesson2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.log4j.Logger;

public class IPinYouMain {
	private static final String HOST = "hdfs://sandbox.hortonworks.com:8020";
	private static final Logger log = Logger.getLogger(IPinYouMain.class);
	private static final Charset charset = Charset.forName("US-ASCII");
	private static FileSystem fs;

	public static void main(String[] args) {
		log.info("Start");
		List<Path> inputFiles = getInputFiles();
		List<Future<Result>> futures = executeWorkers(inputFiles);
		List<Result> results = waitResults(futures);
		Result join = joinResults(results);
		Result sorted = sortResultByValue(join);
		writeResultToFile(sorted);
		log.info("Finish");
	}

	private static FileSystem getFS() {
		try {
			if (fs == null) {
				Configuration conf = new Configuration();
				conf.set("fs.defaultFS", HOST);
				conf.set("fs.hdfs.impl", DistributedFileSystem.class.getName());
				fs = FileSystem.get(conf);
			}
			return fs;
		} catch (IOException e) {
			throw new RuntimeException("Can't get FileSystem", e);
		}
	}

	private static List<Path> getInputFiles() {
		try {
			final String inputDir = "/user/admin/ipinyou";
			Path path = new Path(HOST, inputDir);
			log.info("Input directory: " + path);
			FileSystem fs = getFS();
			RemoteIterator<LocatedFileStatus> files = fs.listFiles(path, false);
			List<Path> paths = new ArrayList<>();
			while (files.hasNext()) {
				paths.add(files.next().getPath());
			}
			log.info("Input files" + paths);
			return paths;
		} catch (IOException e) {
			throw new RuntimeException("Can't read list files in the input directory", e);
		}
	}

	private static List<Future<Result>> executeWorkers(List<Path> inputFiles) {
		ExecutorService executor = Executors.newFixedThreadPool(inputFiles.size());
		FileSystem fs = getFS();
		List<Future<Result>> futures = new ArrayList<>(inputFiles.size());
		for (Path path : inputFiles) {
			try {
				FSDataInputStream is = fs.open(path);
				Worker worker = new Worker(is, charset);
				futures.add(executor.submit(worker));
			} catch (IOException e) {
				log.error("Can't open path: " + path, e);
			}
		}
		executor.shutdown();
		log.info("Workers run");
		return futures;
	}

	private static Result sortResultByValue(Result join) {
		Map<String, Integer> map = join.getIdCountMap().entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		return new Result(map);
	}

	private static Result joinResults(List<Result> results) {
		Map<String, Integer> joinResult = new HashMap<>();
		for (Result result : results) {
			final Map<String, Integer> map = result.getIdCountMap();
			for (Entry<String, Integer> entry : map.entrySet()) {
				final String key = entry.getKey();
				final Integer value = entry.getValue();
				final Integer joinValue = joinResult.get(key);
				if (joinValue == null) {
					joinResult.put(key, value);
				} else {
					Integer newValue = joinValue + value;
					joinResult.put(key, newValue);
				}
			}
		}
		return new Result(joinResult);
	}

	private static List<Result> waitResults(List<Future<Result>> futures) {
		log.info("Wait workers");
		List<Result> results = new ArrayList<>(futures.size());
		for (Future<Result> future : futures) {
			while (!future.isDone() && !future.isCancelled())
				;
			try {
				results.add(future.get());
			} catch (InterruptedException | ExecutionException e) {
				log.error("Can't process future. Skip it.", e);
			}
		}
		log.info("Workers finished");
		return results;
	}

	private static void writeResultToFile(Result sorted) {
		try {
			FileSystem fs = getFS();
			final Path outputFile = new Path(HOST, "/user/admin/ipinyou/out/bid_result.txt");
			log.info("Output file: " + outputFile);
			try (final FSDataOutputStream dos = fs.create(outputFile)) {
				for (Entry<String, Integer> entry : sorted.getIdCountMap().entrySet()) {
					String line = entry.getKey() + " - " + entry.getValue() + "\n";
					dos.writeUTF(line);
				}
			}
			log.info("The output file is writed");
		} catch (IOException e) {
			new RuntimeException("Can't store result to file", e);
		}
	}
}
