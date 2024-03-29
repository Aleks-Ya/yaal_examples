package lang.generics.clazzes;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Implement PECS principle (Producer Extends Consumer Super).
 * If you are only pulling items from a generic collection, it is a producer and you should use "extends";
 * if you are only stuffing items in, it is a consumer and you should use "super".
 * If you do both with the same collection, you shouldn't use either "extends" or "super".
 */
class PecsObjectTest {

    /**
     * If you are only pulling items from a generic collection, it is a producer and you should use "extends";
     */
    @Test
    void producerExtends() {
        Cat cat = new Cat();
        Producer<? extends Creature> producer = new Producer<>(cat);
        Creature creature = producer.get();
        assertThat(creature).isEqualTo(cat);
    }

    /**
     * if you are only stuffing items in, it is a consumer and you should use "super".
     */
    @Test
    void consumerSuper() {
        Cat cat = new Cat();
        Consumer<? super Creature> consumer = new Consumer<>();
        consumer.put(cat);
        assertThat(consumer.toString()).isEqualTo("Cat");
    }

    /**
     * If you do both with the same collection, you shouldn't use either "extends" or "super".
     */
    @Test
    void producerAndConsumer() {
        Cat cat = new Cat();
        Storage<Creature> storage = new Storage<>();
        storage.put(cat);
        Creature act = storage.get();
        assertThat(act).isEqualTo(cat);
    }

    private static class Storage<T> {
        T element;

        void put(T element) {
            this.element = element;
        }

        T get() {
            return element;
        }
    }

    private static class Producer<T> {
        T element;

        Producer(T element) {
            this.element = element;
        }

        T get() {
            return element;
        }
    }

    private static class Consumer<T> {
        T element;

        void put(T element) {
            this.element = element;
        }

        @Override
        public String toString() {
            return element.getClass().getSimpleName();
        }
    }

    private static class Creature {
    }

    private static class Animal extends Creature {
    }

    private static class Cat extends Animal {
    }
}
