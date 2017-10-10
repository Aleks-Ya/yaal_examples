package lang.inheritance.abstract_operator.implement;

/**
 * Обязан ли абстрактный класс декларировать абстрактные методы
 * для всех методов интерфейса?
 * Не обязан.
 */
interface Interface {
    int getInt();
}

abstract class Abstract implements Interface {
}