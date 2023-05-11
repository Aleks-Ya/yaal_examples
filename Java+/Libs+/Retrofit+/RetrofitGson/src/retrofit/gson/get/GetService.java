package retrofit.gson.get;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.Objects;

interface GetService {
    @GET("persons/manager")
    Call<Person> getManager();
}

final class Person {
    private String name;

    Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Person) obj;
        return Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Person[" +
                "name=" + name + ']';
    }

}
