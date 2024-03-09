package gptui.model.storage;

public record Theme(ThemeId id, String title) {
    @Override
    public String toString() {
        return title;
    }
}
