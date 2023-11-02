package serialization;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;


class JsonObjectToJsonTest {
    @Test
    void compact() {
        var rootJsonObject = new JsonObject();
        rootJsonObject.addProperty("name", "John");
        rootJsonObject.addProperty("age", 30);
        var workJsonObject = new JsonObject();
        workJsonObject.addProperty("company", "Google");
        workJsonObject.addProperty("department", "IT");
        rootJsonObject.add("work", workJsonObject);

        var builder = new GsonBuilder();
        var gson = builder.create();

        var jsonStr = gson.toJson(rootJsonObject);
        assertThatJson(jsonStr).isEqualTo("{name:John, age:30, work: {company:Google, department:IT}}");
    }
}
