package joplin.rest.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JoplinService {
    private final JoplinTagApi joplinTagApi;

    public JoplinService() {
        var homeDir = FileUtil.getUserHome();
        var tokenFile = new File(homeDir, ".joplin_rest_api/token.txt");
        var token = FileUtil.fileToString(tokenFile);
        if (token.isBlank()) {
            throw new IllegalStateException("Token is empty: '" + token + "'");
        }
        var baseUrl = "http://localhost:41184";
        var mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    var originalRequest = chain.request();
                    var urlWithParam = originalRequest.url().newBuilder().addQueryParameter("token", token).build();
                    var requestBuilder = originalRequest.newBuilder().url(urlWithParam);
                    return chain.proceed(requestBuilder.build());
                })
                .build();
        var retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .client(client)
                .build();
        joplinTagApi = retrofit.create(JoplinTagApi.class);
    }

    public List<Item> getAllItems() throws IOException {
        var limit = 100;
        var page = 1;
        var tagPage = new ItemsPage(List.of(), true);
        var tags = new ArrayList<Item>();
        while (tagPage.has_more()) {
            tagPage = getTags(limit, page);
            tags.addAll(tagPage.items());
            page++;
        }
        return tags;
    }

    private ItemsPage getTags(int limit, int page) throws IOException {
        var response = joplinTagApi.getAllTags(limit, page).execute();
        if (response.code() != 200) {
            throw new IllegalStateException("Wrong response code: " + response.code());
        }
        return response.body();
    }

    public void renameTag(Item renamedTag) throws IOException {
        System.out.print("Renaming tag to '" + renamedTag.title() + "'...");
        var call = joplinTagApi.renameTag(renamedTag.id(), new RenameTag(renamedTag.title()));
        var response = call.execute();
        if (response.code() != 200) {
            throw new IllegalStateException("Wrong response code: " + response.code());
        }
        System.out.println(" Renamed.");
    }

    public boolean isEmptyTag(String tagId) {
        try {
            var response = joplinTagApi.getTagNotes(tagId, 1, 1).execute();
            if (response.code() != 200) {
                throw new IllegalStateException("Wrong response code: " + response.code());
            }
            var body = response.body();
            return body == null || body.items().isEmpty();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTag(Item tag) {
        try {
            System.out.print("Deleting tag '" + tag.title() + "'...");
            var call = joplinTagApi.deleteTag(tag.id());
            var response = call.execute();
            if (response.code() != 200) {
                throw new IllegalStateException("Wrong response code: " + response.code());
            }
            System.out.println(" Deleted.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
