package bidirectional.subscription;

import java.io.Serializable;

public class Data implements Serializable {
    private String value;

    public Data(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Data2{" +
                "value='" + value + '\'' +
                '}';
    }
}
