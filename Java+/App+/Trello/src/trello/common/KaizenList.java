package trello.common;

public enum KaizenList {
    THOUGHTS("Мысли"),
    NEED_TO_DO("Нужно сделать"),
    PLANNED("Запланировано"),
    IN_PROGRESS_MY("В процессе (мяч на моей стороне)"),
    IN_PROGRESS_OTHER("В процессе (мяч на стороне исполнителя)"),
    TODAY("Сегодня"),
    DONE("Сделано");

    private final String name;

    KaizenList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
