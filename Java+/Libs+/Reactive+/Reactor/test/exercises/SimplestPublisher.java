package exercises;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Передать из Publisher в Subscriber числа 1, 2 и 3.
 */
public class SimplestPublisher {
    @Test
    void test() {
        Publisher<Integer> publisher = new IntegerPublisher();
        Subscriber<Integer> subscriber = new IntegerSubscriber();
        publisher.subscribe(subscriber);
    }

    private static class IntegerSubscriber implements Subscriber<Integer> {

        @Override
        public void onSubscribe(Subscription s) {
            System.out.println("Subscribed");
            s.request(5);
        }

        @Override
        public void onNext(Integer o) {
            System.out.println(o);
        }

        @Override
        public void onError(Throwable t) {
            System.out.println("Error: " + t.getMessage());
        }

        @Override
        public void onComplete() {
            System.out.println("Complete");
        }
    }

    private static class IntegerPublisher implements Publisher<Integer> {
        private Integer[] nums = {1, 2, 3};
        private int index = 0;
        private final int maxIndex = nums.length - 1;

        @Override
        public void subscribe(Subscriber<? super Integer> s) {
            Subscription subscription = new Subscription() {
                @Override
                public void request(long n) {
                    System.out.println("Requested: " + n);
                    while (index <= maxIndex && n > 0) {
                        Integer num = nums[index++];
                        s.onNext(num);
                        n--;
                    }
                    if (index >= maxIndex) {
                        s.onComplete();
                    }
                }

                @Override
                public void cancel() {
                    System.out.println("Canceled");
                }
            };
            s.onSubscribe(subscription);
        }
    }
}
