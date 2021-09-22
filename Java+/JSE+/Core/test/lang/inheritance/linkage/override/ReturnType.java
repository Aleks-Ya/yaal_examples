package lang.inheritance.linkage.override;

import org.junit.jupiter.api.Test;

/**
 * Переопределяющий метод может декларировать возвращаемый параметр дочернего класса.
 */
class ReturnType {
    @Test
    void main() {
        Child child = new Child();
        Result r = child.makeResult();
        System.out.println(r.getClass());
        System.out.println(child.getNum());
    }

    private static class Parent {
        Result makeResult() {
            return new Result();
        }

        long getNum() {
            return 1;
        }
    }

    private static class Child extends Parent {
        @Override
        SubResult makeResult() {
            return new SubResult();
        }

        /**
         * Нельзя вместо long поставить int или byte. Произойдет ошибка компиляции: error: getNum() in Child cannot override getNum() in Parent
         */
        @Override
        long getNum() {
            return 2;
        }
    }

    private static class Result {
    }

    private static class SubResult extends Result {
    }

}