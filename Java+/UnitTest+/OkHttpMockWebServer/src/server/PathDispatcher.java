package server;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PathDispatcher extends Dispatcher {
    private final Map<String, MockResponse> pathToResponse = new HashMap<>();

    @NotNull
    @Override
    public MockResponse dispatch(RecordedRequest recordedRequest) {
        var path = recordedRequest.getPath();
        var mockResponse = pathToResponse.get(path);
        if (mockResponse == null) {
            mockResponse = new MockResponse()
                    .setResponseCode(404)
                    .setBody("MockResponse for path is not set: " + path);
        }
        return mockResponse;
    }

    public PathDispatcher addPathResponse(String path, MockResponse mockResponse) {
        path = addLeadingWithSlash(path);
        pathToResponse.put(path, mockResponse);
        return this;
    }

    private static String addLeadingWithSlash(String path) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return path;
    }
}
