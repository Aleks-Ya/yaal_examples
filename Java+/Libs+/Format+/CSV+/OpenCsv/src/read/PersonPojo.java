package read;

import java.util.Objects;

public final class PersonPojo {
    private Integer id;
    private String name;
    private String gender;

    public PersonPojo() {
    }

    public PersonPojo(Integer id, String name, String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    public Integer id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String gender() {
        return gender;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PersonPojo) obj;
        return Objects.equals(this.id, that.id) &&
               Objects.equals(this.name, that.name) &&
               Objects.equals(this.gender, that.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gender);
    }

    @Override
    public String toString() {
        return "Person2[" +
               "id=" + id + ", " +
               "name=" + name + ", " +
               "gender=" + gender + ']';
    }

}
