package context.property.configuration_properties;

record Person(String name, int age) {
    @Override
    public String toString() {
        return name + "-" + age;
    }
}
