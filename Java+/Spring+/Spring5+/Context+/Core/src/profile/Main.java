package profile;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Способы выбрать активные профили:
 * 1. context.getEnvironment().addActiveProfile("rough");
 * 2. аргумент JVM: -Dspring.profiles.active=rough
 * 3. переменные окружения: export SPRING_PROFILES_ACTIVE=rough
 */
public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext();
        context.getEnvironment().addActiveProfile("rough");
        context.scan("profile.provider");
        context.refresh();
    }
}