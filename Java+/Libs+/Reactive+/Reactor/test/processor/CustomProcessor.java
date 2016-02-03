package processor;

import org.junit.Test;
import org.reactivestreams.Processor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.rx.Stream;
import reactor.rx.Streams;

/**
 * Не доделал.
 */
public class CustomProcessor {
    Processor<String, Integer> strToIntProcessor = new Processor<String, Integer>() {
        @Override
        public void subscribe(Subscriber<? super Integer> s) {
            Subscription subscription = new Subscription() {
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

        private Subscription subscription;

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
    public void name() {
        Stream<String> stream = Streams.just("1", "2");
        stream.process(strToIntProcessor).consume(System.out::println);
    }
}
