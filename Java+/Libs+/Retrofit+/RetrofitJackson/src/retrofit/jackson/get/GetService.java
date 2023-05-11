package retrofit.jackson.get;

import retrofit2.Call;
import retrofit2.http.GET;

interface GetService {
    @GET("persons/manager")
    Call<Person> getManager();

    record Person(String name) {
    }
}
