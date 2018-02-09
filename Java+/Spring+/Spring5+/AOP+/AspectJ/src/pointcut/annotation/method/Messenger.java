package pointcut.annotation.method;


public class Messenger {
    @HelloMethod
    public void writeMessage() {
        System.out.print("Pointcut Annotation Method");
    }
}
