package input_stream;

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

	/**
	 * Все строки сразу загружаются в память.
	 */
	@Test
	public void byBufferedReader1() throws IOException {
		String str;
		try (InputStreamReader isr = new InputStreamReader(input); BufferedReader buffer = new BufferedReader(isr)) {
			str = buffer.lines().collect(Collectors.joining("\n"));
		}
		assertThat(str, equalTo(CONTENT));
	}

	@Test
	public void byInputStreamReader() throws IOException {
		StringBuilder result = new StringBuilder();

		Charset charset = Charset.forName("UTF-8");
		try (InputStreamReader isr = new InputStreamReader(input, charset)) {
			char[] c = new char[1];
			while (isr.read(c) != -1) {
				result.append(c);
			}
		}

		assertThat(result.toString(), equalTo(CONTENT));
	}

	/**
	 * Обработка файлов, не помещающихся в память.
	 */
	@Test
	public void byBufferedReader2() throws IOException {
		Charset charset = Charset.forName("UTF-8");

		StringBuilder result = new StringBuilder();
		try (InputStreamReader isr = new InputStreamReader(input, charset);
				BufferedReader br = new BufferedReader(isr)) {
			String s;
			while ((s = br.readLine()) != null) {
				result.append(s);
			}
		}

		String expected = CONTENT.replace("\n", "");
		assertThat(result.toString(), equalTo(expected));
	}
}
