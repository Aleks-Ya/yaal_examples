package trello;

enum CoverColor {
    PINK("pink"),
    YELLOW("yellow"),
    LIME("lime"),
    BLUE("blue"),
    BLACK("black"),
    ORANGE("orange"),
    RED("red"),
    PURPLE("purple"),
    SKY("sky"),
    GREEN("green");
    private final String color;

    CoverColor(String color) {
        this.color = color;
    }


    public String getColor() {
        return color;
    }
}
