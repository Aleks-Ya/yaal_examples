package custom_runner.test_hierarchy.use_junit_runner;

public class State {
    public String message;

    public State(String message) {
        this.message = message;
    }

    @Override
    protected Object clone() {
        return new State(message);
    }
}
