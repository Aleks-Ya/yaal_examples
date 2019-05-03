package spel;

public class Inventor {
    private String name;
    private Integer id;

    public Inventor(Integer id) {
        this.id = id;
    }

    public Inventor(String name) {
        this.name = name;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
