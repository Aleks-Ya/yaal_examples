package lang.generics.clazzes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Implement PECS principle (Producer Extends Consumer Super).
 * If you are only pulling items from a generic collection, it is a producer and you should use "extends";
 * if you are only stuffing items in, it is a consumer and you should use "super".
 * If you do both with the same collection, you shouldn't use either "extends" or "super".
 */
public class PecsCollection {

    /**
     * If you are only pulling items from a generic collection, it is a producer and you should use "extends";
     */
    @Test
    public void producerExtends() {
        Cat cat = new Cat();
        Animal animal = new Animal();
        List<? extends Creature> list = List.of(cat, animal);

        Cat actCat = (Cat) list.get(0);
        Animal actCat2 = (Animal) list.get(0);
        Creature actCat3 = list.get(0);
        assertThat(actCat, equalTo(cat));
        assertThat(actCat2, equalTo(cat));
        assertThat(actCat3, equalTo(cat));

        Animal actAnimal = (Animal) list.get(1);
        assertThat(actAnimal, equalTo(animal));
    }

    /**
     * if you are only stuffing items in, it is a consumer and you should use "super".
     */
    @Test
    public void consumerSuper() {
        List<? super Creature> list = new ArrayList<>();
        Cat cat = new Cat();
        Animal animal = new Animal();
        list.add(cat);
        list.add(animal);
        assertThat(list, equalTo(List.of(cat, animal)));
    }

    /**
     * If you do both with the same collection, you shouldn't use either "extends" or "super".
     */
    @Test
    public void producerAndConsumer() {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal());
        animals.add(new Cat());

        List<Cat> cats = new ArrayList<>();
        cats.add(new Cat());
        cats.add(new Cat());

        Storage<List<? extends Animal>> storage = new Storage<>();

        storage.put(animals);
        List<? extends Creature> actAnimals = storage.get();

        storage.put(cats);
        List<? extends Creature> actCats = storage.get();
        assertThat(actAnimals, equalTo(animals));
        assertThat(actCats, equalTo(cats));
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

    private static class Creature {
    }

    private static class Animal extends Creature {
    }

    private static class Cat extends Animal {
    }
}
