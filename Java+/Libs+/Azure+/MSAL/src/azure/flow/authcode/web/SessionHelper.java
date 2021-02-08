package azure.flow.authcode.web;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;

class SessionHelper {
    private static final String STATE_REQUEST_PARAM = "state";
    private static final String STATES_ATTR = "STATES";
    private static final String ACCESS_TOKEN_ATTR = "ACCESS_TOKEN";

    public static String saveState(HttpServletRequest request, String targetUrlPath) {
        var states = (Map<String, State>) request.getSession().getAttribute(STATES_ATTR);
        if (states == null) {
            states = new HashMap<>();
            request.getSession().setAttribute(STATES_ATTR, states);
        }
        var id = UUID.randomUUID().toString();
        states.put(id, new State(targetUrlPath));
        return id;
    }

    public static State getState(HttpServletRequest request) {
        var stateId = request.getParameter(STATE_REQUEST_PARAM);
        if (stateId == null) {
            throw new IllegalStateException(format("'%s' request parameter is not found", STATE_REQUEST_PARAM));
        }
        var states = (Map<String, State>) request.getSession().getAttribute(STATES_ATTR);
        var state = states != null ? states.get(stateId) : null;
        if (state == null) {
            throw new IllegalStateException("No state is found in cache for id=" + stateId);
        }
        return state;
    }

    public static Optional<String> getAccessToken(HttpServletRequest request) {
        return Optional.ofNullable((String) request.getSession().getAttribute(ACCESS_TOKEN_ATTR));
    }

    public static void setAccessToken(HttpServletRequest request, String accessToken) {
        System.out.println("Set Access Token to session: " + accessToken);
        request.getSession().setAttribute(ACCESS_TOKEN_ATTR, accessToken);
    }
}
