/**
 * Класс бина для инициализации.
 */
public class MyClass {
    private String value;

    public MyClass(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof MyClass) || value == null) {
            return false;
        } else {
            return value.equals(((MyClass) obj).value);
        }
    }
}