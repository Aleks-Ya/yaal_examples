package retrofit.jackson.put;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

interface PutService {
    @PUT("/persons/")
    Call<Void> putPerson(@Body Person person);

    @PUT("/persons/{id}")
    Call<Void> putPersonId(@Path("id") Integer id, @Body Person person);

    record Person(String name) {
    }
}
