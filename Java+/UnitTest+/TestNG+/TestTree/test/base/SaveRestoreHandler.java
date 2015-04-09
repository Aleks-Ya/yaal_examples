package base;

import java.util.HashMap;
import java.util.Map;

public class SaveRestoreHandler {
    public static final Map<String, State> states = new HashMap<String, State>();

    void save(String groupName, State state) {
        states.put(groupName, state);
    }

    State load(String groupName) {
        State state = states.get(groupName);
        if (state != null) {
            return state;
        } else {
            return new State("Initial state");
//            state =
//            states.put(groupName, state);
//            return state;
        }
    }
}