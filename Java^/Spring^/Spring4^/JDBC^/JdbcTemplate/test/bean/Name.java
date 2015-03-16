package bean;

/**
 * Бин для данных в тестовой таблице.
 */
public class Name {
    private Integer id;
    private String title;

    public Name(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
