package reflection.annotation_processor.print_method_name;

public class ClassForProcessing {

    @PrintMethodNameAnnotation
    public void methodA() {
        System.out.println("method A body");
    }

    @PrintMethodNameAnnotation
    public void methodB() {
        System.out.println("method B body");
    }
}
