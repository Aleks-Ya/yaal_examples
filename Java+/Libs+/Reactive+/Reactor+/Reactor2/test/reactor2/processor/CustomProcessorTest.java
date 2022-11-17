package reactor2.processor;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Processor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.rx.Streams;

/**
 * Не доделал.
 */
class CustomProcessorTest {
    Processor<String, Integer> strToIntProcessor = new Processor<String, Integer>() {
        private Subscription subscription;

        @Override
        public void subscribe(Subscriber<? super Integer> s) {
            var subscription = new Subscription() {
                @Override
                public void request(long n) {
                    "".toString();
                }

                @Override
                public void cancel() {
                    "".toString();
                }
            };
            s.onSubscribe(subscription);
        }

        @Override
        public void onSubscribe(Subscription s) {
            subscription = s;
        }

        @Override
        public void onNext(String s) {
            "".toString();
        }

        @Override
        public void onError(Throwable t) {
            "".toString();
        }

        @Override
        public void onComplete() {
            "".toString();
        }
    };

    @Test
    void name() {
        var stream = Streams.just("1", "2");
        stream.process(strToIntProcessor).consume(System.out::println);
    }
}
