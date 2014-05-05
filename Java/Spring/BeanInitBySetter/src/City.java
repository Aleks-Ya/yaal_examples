public class City {
    private String name;
    private int population;
    private CountryEnum country;

    public void setName(String name) {
        this.name = name;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setCountry(CountryEnum country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return String.format("City[name='%s' population='%d' country='%s']", name, population, country);
    }
}
