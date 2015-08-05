import java.util.ArrayList;
import java.util.List;

public class Country {
    private String name;
    private List<City> cities;

    public Country() {
        setCities(new ArrayList<City>());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", cities=" + cities +
                '}';
    }
}