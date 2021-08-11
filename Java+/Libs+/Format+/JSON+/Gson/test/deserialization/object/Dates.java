package deserialization.object;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

/**
 * Парсинг JSON в Java-объект.
 */
class Dates {
	private static final Gson gson = new Gson();

	@Test
	void dateDefault() {
		var json = "\"Jul 8, 2015 6:37:19 AM\"";
		assertEquals(new Date(1436326639000L), gson.fromJson(json, Date.class));
	}

	@Test
	void dateFormatted() {
		var gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		var json = "\"2015-07-08 06:37:19\"";
		assertEquals(new Date(1436326639000L), gson.fromJson(json, Date.class));
	}
}
