package storage;

public interface Storage {
    void saveAuthorizationCode(String sessionId, String authorizationCode);

    void saveAccessToken(String sessionId, String accessToken);

    void saveRefreshToken(String sessionId, String refreshToken);

    String readRefreshToken(String sessionId);

    String readAccessToken(String sessionId);

    String readAuthenticationCode(String sessionId);
}
