package reactor3.publisher;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class FluxTest {
    @Test
    void just() {
        Flux.just("John", "Mary").map(String::toUpperCase).subscribe(System.out::println);
    }

    @Test
    void collectList_block() {
        var list = Flux.just("John", "Mary").map(String::toUpperCase).collectList().block();
        assertThat(list).containsExactly("JOHN", "MARY");
    }

    @Test
    void collectList_subscribe() {
        var list = new ArrayList<String>();
        Flux.just("John", "Mary").map(String::toUpperCase).collectList().subscribe(list::addAll);
        assertThat(list).containsExactly("JOHN", "MARY");
    }

    @Test
    void blockFirst() {
        var first = Flux.just("John", "Mary").map(String::toUpperCase).blockFirst();
        assertThat(first).isEqualTo("JOHN");
    }
}
