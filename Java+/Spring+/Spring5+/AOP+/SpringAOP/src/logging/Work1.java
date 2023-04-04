package logging;

class Work1 implements IWork {
    @Override
    public String doSomething(int number) {
        return "worker impl response: " + number;
    }
}
