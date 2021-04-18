package bigdata.mapreduce.v2;

import java.util.Set;

public class Result {
	private Set<String> words;
	private int maxLength;

	public Result(Set<String> words, int maxLength) {
		super();
		this.words = words;
		this.maxLength = maxLength;
	}

	public Set<String> getWords() {
		return words;
	}

	public int getMaxLength() {
		return maxLength;
	}

}
