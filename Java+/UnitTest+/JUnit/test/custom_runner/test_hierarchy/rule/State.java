package custom_runner.test_hierarchy.rule;

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
