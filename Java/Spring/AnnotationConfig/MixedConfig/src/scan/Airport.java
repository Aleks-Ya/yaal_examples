package scan;

/**
 * Внедрение зависимостей через конструктор.
 */
public class Airport {
    private String name;

    public Airport(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}