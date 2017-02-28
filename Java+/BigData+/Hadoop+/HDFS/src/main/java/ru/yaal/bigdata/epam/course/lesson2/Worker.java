package ru.yaal.bigdata.epam.course.lesson2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

public class Worker implements Callable<Result> {
	private static final Logger log = Logger.getLogger(Worker.class);
	private final Map<String, Integer> resultMap = new HashMap<>();
	private final InputStream is;
	private final Charset charset;

	public Worker(InputStream is, Charset charset) {
		this.is = is;
		this.charset = charset;
	}

	@Override
	public Result call() throws Exception {
		try (InputStreamReader reader = new InputStreamReader(is, charset);
				BufferedReader br = new BufferedReader(reader)) {
			long counter = 0;
			String line;
			while ((line = br.readLine()) != null) {
				processLine(line);
				counter++;
			}
			log.info("Processed lines: " + counter);
			return new Result(resultMap);
		}
	}

	private void processLine(String line) {
		if (line == null || line.isEmpty()) {
			log.warn("Skip empty line");
			return;
		}
		final String[] fields = line.split("\t");
		final int index = 2;
		if (index >= fields.length) {
			log.warn("No element with index " + index + " in line \"" + line + "\".");
			return;
		}
		String id = fields[index];
		final Integer value = resultMap.get(id);
		if (value == null) {
			resultMap.put(id, 1);
		} else {
			resultMap.put(id, value + 1);
		}
	}

}
