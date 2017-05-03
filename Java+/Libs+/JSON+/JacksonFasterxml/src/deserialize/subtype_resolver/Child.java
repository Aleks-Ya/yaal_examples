package deserialize.subtype_resolver;

@SuppressWarnings({"WeakerAccess", "unused"})
class Child extends Parent {
    private Integer number;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return Child.class.getSimpleName() + "{" + "name=" + getName() + ", number=" + number + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Child) {
            Child o = (Child) obj;
            boolean nameEquals = getName() != null ? getName().equals(o.getName()) : false;
            boolean numberEquals = number != null ? number.equals(o.number) : false;
            return nameEquals && numberEquals;
        }
        return false;
    }
}
