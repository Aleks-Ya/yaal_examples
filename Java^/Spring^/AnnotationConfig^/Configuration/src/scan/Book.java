package scan;

public class Book {
    private int code;
    private String name;

    public Book(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Book[code=%d name=%s]", code, name);
    }
}