package app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
class Data {
    @Value("file:/etc/creds/username1")
    private Resource username1;
    @Value("file:/etc/creds/password1")
    private Resource password1;
    @Value("file:/etc/creds/username2")
    private Resource username2;
    @Value("file:/etc/creds/password2")
    private Resource password2;
    @Value("${city.name}")
    private String cityName;
    @Value("${color}")
    private String color;

    private static String resourceToString(Resource resource) throws IOException {
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    public String getUsername1() throws IOException {
        return resourceToString(username1);
    }

    public String getPassword1() throws IOException {
        return resourceToString(password1);
    }

    public String getUsername2() throws IOException {
        return resourceToString(username2);
    }

    public String getPassword2() throws IOException {
        return resourceToString(password2);
    }

    public String getCityName() {
        return cityName;
    }

    public String getColor() {
        return color;
    }
}
