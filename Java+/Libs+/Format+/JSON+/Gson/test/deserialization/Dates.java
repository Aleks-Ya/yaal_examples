package deserialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

/**
 * Парсинг JSON в Java-объект.
 */
public class Dates {
	private static final Gson gson = new Gson();

	@Test
	public void dateDefault() {
		String json = "\"Jul 8, 2015 6:37:19 AM\"";
		assertEquals(new Date(1436326639000L), gson.fromJson(json, Date.class));
	}

	@Test
	public void dateFormatted() {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String json = "\"2015-07-08 06:37:19\"";
		assertEquals(new Date(1436326639000L), gson.fromJson(json, Date.class));
	}
}