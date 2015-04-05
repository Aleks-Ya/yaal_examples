import java.util.concurrent.Callable;

public class Main {
    public static void main(String[] args) {
        // since Java 8
        // CallableExecutor[] callableAnnotations = OneClass.class.getAnnotationsByType(CallableExecutor.class);
        CallableExecutor[] callableAnnotations = null;
        Class<? extends Callable> callableClass = callableAnnotations[0].callable();
        try {
            Callable callable = callableClass.newInstance();
            callable.call();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
