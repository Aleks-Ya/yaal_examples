package lang.generics.clazzes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Implement PECS principle (Producer Extends Consumer Super).
 * If you are only pulling items from a generic collection, it is a producer and you should use "extends";
 * if you are only stuffing items in, it is a consumer and you should use "super".
 * If you do both with the same collection, you shouldn't use either "extends" or "super".
 */
class PecsCollectionTest {
    private static final Cat cat = new Cat();
    private static final Animal animal = new Animal();
    private static final Creature creature = new Creature();

    /**
     * If you are only pulling items from a generic collection, it is a producer and you should use "extends";
     */
    @Test
    void producerExtends() {
        List<? extends Creature> list;
        {
            List<Creature> creatureList = new ArrayList<>();
            creatureList.add(cat);
            creatureList.add(animal);
            creatureList.add(creature);

            list = creatureList;

            var actCat = (Cat) list.get(0);
            var actAnimal = (Animal) list.get(1);
            var actCreature = list.get(2);
            assertThat(actCat).isEqualTo(cat);
            assertThat(actAnimal).isEqualTo(animal);
            assertThat(actCreature).isEqualTo(creature);
        }
        {
            List<Animal> animalList = new ArrayList<>();
            animalList.add(cat);
            animalList.add(animal);

            list = animalList;

            var actAnimal = (Animal) list.get(0);
            var actCreature = list.get(1);
            assertThat(actAnimal).isEqualTo(cat);
            assertThat(actCreature).isEqualTo(animal);
        }
        {
            List<Cat> catList = new ArrayList<>();
            catList.add(cat);

            list = catList;
            var actCreature = list.get(0);
            assertThat(actCreature).isEqualTo(cat);
        }
    }

    /**
     * if you are only stuffing items in, it is a consumer and you should use "super".
     */
    @Test
    void consumerSuper() {
        List<? super Creature> list = new ArrayList<>();
        list.add(cat);
        list.add(animal);
        list.add(creature);

        assertThat(list).isEqualTo(List.of(cat, animal, creature));
    }

    /**
     * if you are only stuffing items in, it is a consumer and you should use "super".
     */
    @Test
    void consumerSuper2() {
        List<? super Cat> list;
        {
            list = new ArrayList<Creature>();
            list.add(cat);
            assertThat(list).isEqualTo(List.of(cat));
        }
        {
            list = new ArrayList<Animal>();
            list.add(cat);
            assertThat(list).isEqualTo(List.of(cat));
        }
        {
            //noinspection Convert2Diamond
            list = new ArrayList<Cat>();
            list.add(cat);
            assertThat(list).isEqualTo(List.of(cat));
        }
    }

    /**
     * If you do both with the same collection, you shouldn't use either "extends" or "super".
     */
    @Test
    void producerAndConsumer1() {
        List<Creature> list = new ArrayList<>();
        list.add(cat);
        list.add(animal);
        list.add(creature);

        var actCat1 = (Cat) list.get(0);
        var actCat2 = (Animal) list.get(0);
        var actCat3 = list.get(0);
        assertThat(actCat1).isEqualTo(cat);
        assertThat(actCat2).isEqualTo(cat);
        assertThat(actCat3).isEqualTo(cat);

        var actAnimal1 = (Animal) list.get(1);
        var actAnimal2 = list.get(1);
        assertThat(actAnimal1).isEqualTo(animal);
        assertThat(actAnimal2).isEqualTo(animal);

        var actCreature = list.get(2);
        assertThat(actCreature).isEqualTo(creature);
    }

    /**
     * If you do both with the same collection, you shouldn't use either "extends" or "super".
     */
    @Test
    void producerAndConsumer2() {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal());
        animals.add(new Cat());

        List<Cat> cats = new ArrayList<>();
        cats.add(new Cat());
        cats.add(new Cat());

        var storage = new Storage<List<? extends Animal>>();

        storage.put(animals);
        List<? extends Creature> actAnimals = storage.get();

        storage.put(cats);
        List<? extends Creature> actCats = storage.get();
        assertThat(actAnimals).isEqualTo(animals);
        assertThat(actCats).isEqualTo(cats);
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
