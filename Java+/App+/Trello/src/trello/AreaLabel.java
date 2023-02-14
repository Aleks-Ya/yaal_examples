package trello;

import java.util.Arrays;
import java.util.Optional;

enum AreaLabel {
    HEALTH("A: Health", Color.BLUE),
    FINANCES("A: Finances", Color.GREEN),
    WORK("A: Work", Color.BLACK),
    RELATIONSHIPS("A: Relationships", Color.PURPLE),
    EMOTIONS("A: Emotions", Color.RED),
    COMMUNITY("A: Community", Color.ORANGE),
    SELF_IMPROVEMENT("A: Self-Improvement", Color.YELLOW);
    private final String title;
    private final Color color;

    AreaLabel(String title, Color color) {
        this.title = title;
        this.color = color;
    }

    public static Optional<AreaLabel> byName(String name) {
        return Arrays.stream(values()).filter(areaLabel -> name.equals(areaLabel.getTitle())).findFirst();
    }

    public String getTitle() {
        return title;
    }

    public Color getCoverColor() {
        return color;
    }
}
