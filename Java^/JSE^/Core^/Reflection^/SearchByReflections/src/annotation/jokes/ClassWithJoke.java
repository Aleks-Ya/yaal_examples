package annotation.jokes;

import annotation.Joke;

@Joke("Врач сказал в морг, значит, в морг")
@SuppressWarnings("unused")
public class ClassWithJoke {
    @Joke("Работа диджея на радио: рот закрыл - рабочее место убрано")
    private int table;

    @Joke("Спарта. Сборная пропасти.")
    public ClassWithJoke() {
    }

    @Joke("Норвежские проститутки посетили Короля Испании с ответным визитом")
    private void cheese(@Joke("Что-то неспокойно в Датском королевстве") @Deprecated String hey){
    }
}
