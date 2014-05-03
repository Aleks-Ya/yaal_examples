public class City {
    private String name;
    private int population;

    public void setName(String name) {
        this.name = name;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return String.format("City[name='%s' population='%d']", name, population);
    }
}
