package elastic.client;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.Test;

import java.io.IOException;

import static elastic.client.SecurityHelper.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BasicAuthentication {
    @Test
    public void credentialsProvider() throws IOException {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(USER_NAME, PASSWORD));

        RestClient client = RestClient.builder(
                new HttpHost(HOST, PORT, SCHEMA))
                .setHttpClientConfigCallback(builder -> builder.setDefaultCredentialsProvider(credentialsProvider))
                .build();
        Response response = client.performRequest("GET", "/");
        client.close();

        System.out.println("Response: " + response);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    @Test
    public void useSecurityHelper() throws IOException {
        RestClient client = RestClient.builder(
                new HttpHost(HOST, PORT, SCHEMA))
                .setHttpClientConfigCallback(SecurityHelper.httpClientConfigCallback)
                .build();
        Response response = client.performRequest("GET", "/");
        client.close();
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

}
