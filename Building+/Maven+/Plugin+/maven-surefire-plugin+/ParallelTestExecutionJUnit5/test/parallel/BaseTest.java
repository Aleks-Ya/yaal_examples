package parallel;


public abstract class BaseTest {
    protected void printCurrentThread() {
        System.out.println(getClass().getSimpleName() + "" + currentThreadName());
    }

    protected static String currentThreadName() {
        return Thread.currentThread().toString();
    }

    protected static void wait3Seconds(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}