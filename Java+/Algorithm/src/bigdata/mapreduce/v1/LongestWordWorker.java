package bigdata.mapreduce.v1;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class LongestWordWorker implements Worker<Result> {

	private final String data;

	public LongestWordWorker(String data) {
		this.data = data;
	}

	@Override
	public Result call() {
		Set<String> longest = new HashSet<>();
		int maxLength = 0;
		final StringTokenizer tokenizer = new StringTokenizer(data, " ");
		while (tokenizer.hasMoreTokens()) {
			// for (String word : data.split(" ")) {
			String word = tokenizer.nextToken();
			if (word.length() == maxLength) {
				longest.add(word);
			}
			if (word.length() > maxLength) {
				longest.clear();
				longest.add(word);
				maxLength = word.length();
			}
		}
		return new Result(longest, maxLength);
	}

}
