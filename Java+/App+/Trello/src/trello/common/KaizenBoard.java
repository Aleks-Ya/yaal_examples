package trello.common;

public enum KaizenBoard {
    KAIZEN_INIT_1("1 Kaizen Init"),
    KAIZEN_2023("2 Kaizen 2023"),
    KAIZEN_2024("3 Kaizen 2024"),
    KAIZEN_MONTH("4 Kaizen Month");

    private final String name;

    KaizenBoard(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
