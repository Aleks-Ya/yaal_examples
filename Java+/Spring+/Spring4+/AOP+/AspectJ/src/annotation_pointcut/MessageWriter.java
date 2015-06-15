package annotation_pointcut;


public class MessageWriter {
    @Hello
    public void writeMessage() {
        System.out.print("World");
    }
}
