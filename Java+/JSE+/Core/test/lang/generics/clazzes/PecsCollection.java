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
    private static final Cat cat = new Cat();
    private static final Animal animal = new Animal();
    private static final Creature creature = new Creature();

    /**
     * If you are only pulling items from a generic collection, it is a producer and you should use "extends";
     */
    @Test
    public void producerExtends() {
        List<? extends Creature> list;
        {
            List<Creature> creatureList = new ArrayList<>();
            creatureList.add(cat);
            creatureList.add(animal);
            creatureList.add(creature);

            list = creatureList;

            Cat actCat = (Cat) list.get(0);
            Animal actAnimal = (Animal) list.get(1);
            Creature actCreature = list.get(2);
            assertThat(actCat, equalTo(cat));
            assertThat(actAnimal, equalTo(animal));
            assertThat(actCreature, equalTo(creature));
        }
        {
            List<Animal> animalList = new ArrayList<>();
            animalList.add(cat);
            animalList.add(animal);

            list = animalList;

            Animal actAnimal = (Animal) list.get(0);
            Creature actCreature = list.get(1);
            assertThat(actAnimal, equalTo(cat));
            assertThat(actCreature, equalTo(animal));
        }
        {
            List<Cat> catList = new ArrayList<>();
            catList.add(cat);

            list = catList;
            Creature actCreature = list.get(0);
            assertThat(actCreature, equalTo(cat));
        }
    }

    /**
     * if you are only stuffing items in, it is a consumer and you should use "super".
     */
    @Test
    public void consumerSuper() {
        List<? super Creature> list = new ArrayList<>();
        list.add(cat);
        list.add(animal);
        list.add(creature);

        assertThat(list, equalTo(List.of(cat, animal, creature)));
    }

    /**
     * if you are only stuffing items in, it is a consumer and you should use "super".
     */
    @Test
    public void consumerSuper2() {
        List<? super Cat> list;
        {
            list = new ArrayList<Creature>();
            list.add(cat);
            assertThat(list, equalTo(List.of(cat)));
        }
        {
            list = new ArrayList<Animal>();
            list.add(cat);
            assertThat(list, equalTo(List.of(cat)));
        }
        {
            //noinspection Convert2Diamond
            list = new ArrayList<Cat>();
            list.add(cat);
            assertThat(list, equalTo(List.of(cat)));
        }
    }

    /**
     * If you do both with the same collection, you shouldn't use either "extends" or "super".
     */
    @Test
    public void producerAndConsumer1() {
        List<Creature> list = new ArrayList<>();
        list.add(cat);
        list.add(animal);
        list.add(creature);

        Cat actCat1 = (Cat) list.get(0);
        Animal actCat2 = (Animal) list.get(0);
        Creature actCat3 = list.get(0);
        assertThat(actCat1, equalTo(cat));
        assertThat(actCat2, equalTo(cat));
        assertThat(actCat3, equalTo(cat));

        Animal actAnimal1 = (Animal) list.get(1);
        Creature actAnimal2 = list.get(1);
        assertThat(actAnimal1, equalTo(animal));
        assertThat(actAnimal2, equalTo(animal));

        Creature actCreature = list.get(2);
        assertThat(actCreature, equalTo(creature));
    }

    /**
     * If you do both with the same collection, you shouldn't use either "extends" or "super".
     */
    @Test
    public void producerAndConsumer2() {
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
