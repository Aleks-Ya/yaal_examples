import org.junit.jupiter.api.Test;
import rx.Observable;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Использование операторов RxJava.
 */
class OperatorsTest {
    @Test
    void map() {
        Observable.just("Hello, world!")
                .map(String::hashCode)
                .map(i -> Integer.toString(i))
                .subscribe(System.out::println);
    }

    @Test
    void filter() {
        Observable.just("A", "", null, "B")
                .filter(s -> s != null)
                .filter(s -> s.length() > 0)
                .subscribe(System.out::println);
    }

    /**
     * Преобразование потока коллекций в поток элементов этих коллекций.
     */
    @Test
    void flatMap() {
        var list1 = Arrays.asList("A", "B");
        var list2 = Arrays.asList("C", "D");
        Observable.just(list1, list2)
                .flatMap(Observable::from)
                .subscribe(System.out::println);
    }

    /**
     * Взять 2 элемента из Источника.
     */
    @Test
    void take() {
        Observable.just("A", "B", "C")
                .take(2)
                .subscribe(System.out::println);
    }

    /**
     * Выполнить действие над каждым элементом Источника.
     */
    @Test
    void doOnNext() {
        var sb = new StringBuilder();
        Observable.just("A", "B")
                .doOnNext(sb::append)
                .subscribe(System.out::println);
        assertThat(sb.toString()).isEqualTo("AB");
    }
}
