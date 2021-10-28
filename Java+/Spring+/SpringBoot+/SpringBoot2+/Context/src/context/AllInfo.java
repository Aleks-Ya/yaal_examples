package context;

public class AllInfo {
    private final Person person;
    private final City city;

    public AllInfo(Person person, City city) {
        this.person = person;
        this.city = city;
    }

    @Override
    public String toString() {
        return person + " from " + city;
    }
}
