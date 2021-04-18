import org.junit.jupiter.api.Test;
import rx.Observable;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Использование операторов RxJava.
 */
public class Operators {
    @Test
    public void map() {
        Observable.just("Hello, world!")
                .map(String::hashCode)
                .map(i -> Integer.toString(i))
                .subscribe(System.out::println);
    }

    @Test
    public void filter() {
        Observable.just("A", "", null, "B")
                .filter(s -> s != null)
                .filter(s -> s.length() > 0)
                .subscribe(System.out::println);
    }

    /**
     * Преобразование потока коллекций в поток элементов этих коллекций.
     */
    @Test
    public void flatMap() {
        List<String> list1 = Arrays.asList("A", "B");
        List<String> list2 = Arrays.asList("C", "D");
        Observable.just(list1, list2)
                .flatMap(Observable::from)
                .subscribe(System.out::println);
    }

    /**
     * Взять 2 элемента из Источника.
     */
    @Test
    public void take() {
        Observable.just("A", "B", "C")
                .take(2)
                .subscribe(System.out::println);
    }

    /**
     * Выполнить действие над каждым элементом Источника.
     */
    @Test
    public void doOnNext() {
        StringBuilder sb = new StringBuilder();
        Observable.just("A", "B")
                .doOnNext(sb::append)
                .subscribe(System.out::println);
        assertThat(sb.toString(), equalTo("AB"));
    }
}
