package self_test_run;

import java.util.HashMap;
import java.util.Map;

public class StateHolder {
    private static final StateHolder INSTANCE = new StateHolder();
    private final Map<Class, State> states = new HashMap<>();
    private static final State INITIAL_STATE = new State("Init state");

    private StateHolder() {}

    public State getState(Class klass) {
        State state = states.get(klass);
        return state != null ? state : INITIAL_STATE;
    }

    public void putState(Class klass, State state) {
        states.put(klass, state);
    }

    public static StateHolder getInstance() {
        return INSTANCE;
    }
}
