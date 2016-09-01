package input_stream_to_string;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Самый простой способ преобразовать InputStream в String.
 */
public class InputStreamToString {
	private static final String CONTENT = "Привет!\nПока!";
	private final InputStream input = new ByteArrayInputStream(CONTENT.getBytes());

	@Test
	public void byBufferedReader() throws IOException {
		BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
		String str = buffer.lines().collect(Collectors.joining("\n"));

		assertThat(str, equalTo(CONTENT));
	}

	@Test
	public void byInputStreamReader() throws IOException {
		Charset charset = Charset.forName("UTF-8");

		InputStreamReader isr = new InputStreamReader(input, charset);
		StringBuilder result = new StringBuilder();
		char[] c = new char[1];
		while (isr.read(c) != -1) {
			result.append(c);
		}

		assertThat(result.toString(), equalTo(CONTENT));
	}
}
