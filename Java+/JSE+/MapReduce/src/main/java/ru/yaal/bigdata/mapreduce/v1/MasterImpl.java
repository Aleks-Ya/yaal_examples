package ru.yaal.bigdata.mapreduce.v1;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class MasterImpl implements Master {

	@Override
	public List<? extends Callable<Result>> map(Reader data, long dataSize, int workerCount) {
		try {
			StringBuilder content = new StringBuilder();
			char[] buffer = new char[1024];
			while ((data.read(buffer)) != -1) {
				content.append(buffer);
			}

			List<Worker<Result>> jobs = new ArrayList<>(workerCount);
			int perWorker = content.length() / workerCount;
			int first = 0;
			int last = 0;
			for (int i = 0; i < workerCount - 1; i++) {
				last = first + perWorker;
				if (last > content.length()) {
					last = content.length() - 1;
				}
				while (last < content.length() && !Character.isWhitespace(content.charAt(last))) {
					last++;
				}
				Worker<Result> worker = new LongestWordWorker(content.substring(first, last));
				jobs.add(worker);
				first = last + 1;
			}
			return jobs;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Set<String> reduce(List<Future<Result>> jobs) {
		try {
			for (Future<Result> future : jobs) {
				while (!future.isDone() && !future.isCancelled())
					;
			}
			Result longest = null;
			for (Future<Result> future : jobs) {
				Result value;
				value = future.get();
				if (longest == null) {
					longest = value;
				} else {
					if (value.getMaxLength() == longest.getMaxLength()) {
						longest.getWords().addAll(value.getWords());
					}
					if (value.getMaxLength() > longest.getMaxLength()) {
						longest = value;
					}
				}
			}
			return longest.getWords();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
