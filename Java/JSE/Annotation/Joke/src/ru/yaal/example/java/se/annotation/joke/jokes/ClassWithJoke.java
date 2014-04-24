package ru.yaal.example.java.se.annotation.joke.jokes;

import ru.yaal.example.java.se.annotation.joke.Joke;

@Joke("Врач сказал в морг, значит, в морг")
@SuppressWarnings("unused")
public class ClassWithJoke {
    @Joke("Работа диджея на радио: рот закрыл - рабочее место убрано")
    private int table;

    @Joke("Норвежские проститутки посетили Короля Испании с ответным визитом")
    private void cheese(){
    }
}
