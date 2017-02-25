package ru.yaal.bigdata.mapreduce.cyclic;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.stream.Collectors;

import org.junit.Test;

public class CyclicFileReaderTest {

	@Test
	public void test() throws IOException {
		Reader origin = new StringReader("abc ");
		int size = 10;
		Reader cyclic = new CyclicReader(origin, size);

		try (BufferedReader br = new BufferedReader(cyclic)) {
			final String actual = br.lines().collect(Collectors.joining());
			final String expected = "abc abc ab";
			assertEquals(expected, actual);
		}
	}

}
