package pointcut.annotation;


public class MessageWriter {
    @Hello
    public void writeMessage() {
        System.out.print("World");
    }
}
