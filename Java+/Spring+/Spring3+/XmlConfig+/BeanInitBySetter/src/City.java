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

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public CountryEnum getCountry() {
        return country;
    }

}