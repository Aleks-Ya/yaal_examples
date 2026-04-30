package serialization;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class EnumToJsonTest {
    private static final Gson gson = new Gson();

    @Test
    void compact() {
        var effort = ReasoningEffort.LOW;
        var jsonStr = gson.toJson(effort);
        assertThat(jsonStr).isEqualTo("\"LOW\"");
    }

    @Test
    void serializedName() {
        var gender = Gender.FEMALE;
        var jsonStr = gson.toJson(gender);
        assertThat(jsonStr).isEqualTo("\"female\"");
    }

    enum ReasoningEffort {
        LOW, MEDIUM, HIGH
    }

    enum Gender {
        @SerializedName("male")
        MALE,
        @SerializedName("female")
        FEMALE
    }

}
