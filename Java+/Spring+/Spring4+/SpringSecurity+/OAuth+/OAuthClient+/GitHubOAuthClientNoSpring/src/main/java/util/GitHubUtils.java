package util;

import static java.lang.String.format;

public class GitHubUtils {
    private static final String USER_INFO_ENDPOINT = "https://api.github.com/user";

    public static String userInfoUrl(String accessToken) {
        return format("%s?access_token=%s", USER_INFO_ENDPOINT,accessToken);
    }

}
