package context.property.configuration_properties;

record AllInfo(Person person, CityOnMethod city) {
    @Override
    public String toString() {
        return person + " from " + city;
    }
}
