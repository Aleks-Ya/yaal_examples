package ru.yaal.bigdata.mapreduce.cyclic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.stream.Collectors;

public class CyclicReader extends Reader {
	private char[] content;
	private final int fileSize;
	private int index;

	public CyclicReader(Reader origin, int fileSize) throws IOException {
		this.fileSize = fileSize;

		BufferedReader bf = new BufferedReader(origin);
		content = bf.lines().collect(Collectors.joining("\n")).toCharArray();
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		if (index >= fileSize) {
			return -1;
		}
		int charReaded = 0;
		index = index + off;
		for (int i = 0; i < len; i++) {
			if (index >= fileSize) {
				break;
			}
			char fakeChar = take(index);
			cbuf[i] = fakeChar;
			charReaded++;
			index++;
		}
		return charReaded;
	}

	private char take(int n) throws IOException {
		int i = n % content.length;
		return content[i];
	}

	@Override
	public void close() throws IOException {
		content = null;
	}
}
