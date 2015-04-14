package custom_runner.self_test_run;

import java.util.HashMap;
import java.util.Map;

public class StateHolder {
    private static final StateHolder INSTANCE = new StateHolder();
    private final Map<Class, State> states = new HashMap<>();

    private StateHolder() {
    }

    public State getState(Class klass) {
        if (klass == null) {
            return new State("Init state");
        }
        State state = states.get(klass);
        if (state == null) {
            throw new AssertionError("State not found: " + klass);
        }
        return (State) state.clone();
    }

    public void putState(Class klass, State state) {
        states.put(klass, state);
    }

    public static StateHolder getInstance() {
        return INSTANCE;
    }
}
