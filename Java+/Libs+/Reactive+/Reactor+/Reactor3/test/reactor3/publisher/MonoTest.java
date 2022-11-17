package reactor3.publisher;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

class MonoTest {
    @Test
    void just() {
        Mono.just("John").map(String::toUpperCase).subscribe(System.out::println);
    }

    @Test
    void block() {
        var result = Mono.just("John").map(String::toUpperCase).block();
        assertThat(result).isEqualTo("JOHN");
    }

}
