public enum CountryEnum {
    RUSSIA("Россия"), USA("США");

    private String name;

    CountryEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}