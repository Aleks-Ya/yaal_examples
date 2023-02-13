package trello;

enum AreaLabel {
    HEALTH("A: Health", CoverColor.BLUE),
    FINANCES("A: Finances", CoverColor.GREEN),
    WORK("A: Work", CoverColor.BLACK),
    RELATIONSHIPS("A: Relationships", CoverColor.PURPLE),
    EMOTIONS("A: Emotions", CoverColor.RED),
    COMMUNITY("A: Community", CoverColor.ORANGE),
    SELF_IMPROVEMENT("A: Self-Improvement", CoverColor.YELLOW);
    private final String name;
    private final CoverColor coverColor;

    AreaLabel(String name, CoverColor coverColor) {
        this.name = name;
        this.coverColor = coverColor;
    }


    public String getName() {
        return name;
    }

    public CoverColor getCoverColor() {
        return coverColor;
    }
}
