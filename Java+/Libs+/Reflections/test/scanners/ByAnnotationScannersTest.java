package scanners;

import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

/**
 * Варианты поиска по аннотациям.
 * <p>
 * Написать аннотацию Joke, имеющую строковый элемент с шуткой.
 * Необходимо найти все шутки в classpath и вывести их в командную строку.
 * Аннтация Joke может быть применена ко всем доступным элементам.
 */
class ByAnnotationScannersTest {

    @Test
    void searchAnnotatedMethods() {
        System.out.println("Method annotations:");
        var reflections = new Reflections(ByAnnotationScannersTest.class.getPackage().getName(), Scanners.MethodsAnnotated);
        var jokeMethods = reflections.getMethodsAnnotatedWith(Joke.class);
        for (var method : jokeMethods) {
            var joke = method.getAnnotation(Joke.class);
            System.out.println(joke.value());
        }
        System.out.println();
    }

    @Test
    void searchAnnotatedParameters() {
        System.out.println("Parameters annotations:");
        var reflections = new Reflections(ByAnnotationScannersTest.class.getPackage().getName(), Scanners.MethodsParameter);
        var jokeMethods = reflections.getMethodsWithParameter(Joke.class);
        for (var method : jokeMethods) {
            var parameterAnnotations = method.getParameterAnnotations();
            for (var annotations : parameterAnnotations) {
                for (var annotation : annotations) {
                    if (annotation instanceof Joke) {
                        var joke = (Joke) annotation;
                        System.out.println(joke.value());
                    }
                }
            }
        }
        System.out.println();
    }

    @Test
    void searchAnnotatedFields() {
        System.out.println("Fields annotations:");
        var reflections = new Reflections(ByAnnotationScannersTest.class.getPackage().getName(), Scanners.FieldsAnnotated);
        var jokeFields = reflections.getFieldsAnnotatedWith(Joke.class);
        for (var field : jokeFields) {
            var joke = field.getAnnotation(Joke.class);
            System.out.println(joke.value());
        }
        System.out.println();
    }

    @Test
    void searchAnnotatedConstructors() {
        System.out.println("Constructor annotations:");
        var reflections = new Reflections(ByAnnotationScannersTest.class.getPackage().getName(), Scanners.MethodsAnnotated);
        var jokeConstructors = reflections.getConstructorsAnnotatedWith(Joke.class);
        for (var constructor : jokeConstructors) {
            var joke = (Joke) constructor.getAnnotation(Joke.class);
            System.out.println(joke.value());
        }
        System.out.println();
    }

    @Test
    void searchAnnotatedClasses() {
        System.out.println("Classes annotations:");
        var reflections = new Reflections(ByAnnotationScannersTest.class.getPackage().getName());
        var jokeClasses = reflections.getTypesAnnotatedWith(Joke.class);
        for (Class<?> jokeClass : jokeClasses) {
            if (!isPackage(jokeClass)) {
                var joke = (Joke) jokeClass.getAnnotation(Joke.class);
                System.out.println(joke.value());
            }
        }
        System.out.println();
    }

    @Test
    public void searchAnnotatedPackages() {
        System.out.println("Packages annotations:");
        var reflections = new Reflections(ByAnnotationScannersTest.class.getPackage().getName());
        var jokeClasses = reflections.getTypesAnnotatedWith(Joke.class);
        for (Class<?> jokeClass : jokeClasses) {
            if (isPackage(jokeClass)) {
                var joke = (Joke) jokeClass.getAnnotation(Joke.class);
                System.out.println(joke.value());
            }
        }
        System.out.println();
    }

    public boolean isPackage(Class<?> clazz) {
        return clazz.getSimpleName().equals("package-info");
    }
}
