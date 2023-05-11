package joplin.rest.common;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface JoplinTagApi {
    @GET("tags")
    Call<ItemsPage> getAllTags(@Query("limit") int limit, @Query("page") int page);

    @PUT("tags/{tagId}")
    Call<Void> renameTag(@Path("tagId") String tagId, @Body RenameTag renameTag);

    @GET("tags/{tagId}/notes")
    Call<ItemsPage> getTagNotes(@Path("tagId") String tagId, @Query("limit") int limit, @Query("page") int page);

    @DELETE("tags/{tagId}")
    Call<Void> deleteTag(@Path("tagId") String tagId);
}
