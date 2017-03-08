package generics.clazzes;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация принципа PECS (Producer Extends Consumer Super).
 */
public class PECS {

    @Test
    public void object() {
        Storage<Animal> storage = new Storage<>();

        Animal primate = new Animal();
        Cat cat = new Cat();
        storage.put(primate);
        storage.put(cat);

        Creature creature = storage.get();
    }

    @Test
    public void collection() {
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

    }

    static class Storage<T> {
        T element;

        void put(T element) {
            this.element = element;
        }

        T get() {
            return element;
        }
    }

    static class Creature {
    }

    static class Animal extends Creature {
    }

    static class Cat extends Animal {
    }
}
