package read;

import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

public final class PersonPojoImmutable {
//    @CsvBindByName
    private final Integer id;
//    @CsvBindByName
    private final String name;
//    @CsvBindByName
    private final String gender;

    public PersonPojoImmutable(
            Integer id, String name, String gender) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PersonPojoImmutable) obj;
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
