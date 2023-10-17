package io.serialization.standard;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SerializationLifecycleTest {
    static final List<String> events = Collections.synchronizedList(new ArrayList<>());

    @Test
    void standard() throws IOException, ClassNotFoundException {
        var originalPerson = new Person();
        originalPerson.setName("John Doe");
        originalPerson.setAge(25);

        assertThat(events).containsExactly(
                "Static field",
                "Static Initialization Block",
                "Instance Initialization Block",
                "Default Constructor");
        events.clear();

        byte[] serializedPerson;
        try (var bos = new ByteArrayOutputStream();
             var out = new ObjectOutputStream(bos)) {
            out.writeObject(originalPerson);
            serializedPerson = bos.toByteArray();
        }

        Person deserializedPerson;
        try (var bis = new ByteArrayInputStream(serializedPerson);
             var in = new ObjectInputStream(bis)) {
            deserializedPerson = (Person) in.readObject();
        }

        assertThat(originalPerson.getName()).isEqualTo(deserializedPerson.getName());
        assertThat(originalPerson.getAge()).isEqualTo(deserializedPerson.getAge());

        assertThat(events).isEmpty();
    }


    private static class Person implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
        private static String species = initSpecies();
        private String name;
        private int age;

        private static String initSpecies() {
            SerializationLifecycleTest.events.add("Static field");
            return "Homo sapiens";
        }

        static {
            SerializationLifecycleTest.events.add("Static Initialization Block");
            species = "Homo sapiens sapiens";
        }

        {
            SerializationLifecycleTest.events.add("Instance Initialization Block");
            this.name = "Unknown";
            this.age = 0;
        }

        public Person() {
            SerializationLifecycleTest.events.add("Default Constructor");
        }

        public Person(String name, int age) {
            SerializationLifecycleTest.events.add("Parameterized Constructor");
            this.name = name;
            this.age = age;
        }

        public static String getSpecies() {
            return species;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}