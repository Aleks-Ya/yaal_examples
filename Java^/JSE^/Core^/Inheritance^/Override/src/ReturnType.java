package type;

/**
 * Переопределяющий метод может декларировать возвращаемый параметр дочернего класса.
 */
public class ReturnType {
    public static void main(String[] args) {
        Result r = new Child().makeResult();
        System.out.println(r.getClass());
    }
}

class Parent {
    Result makeResult() {
        return new Result();
    }
}

class Child extends Parent {
    @Override
    SubResult makeResult() {
        return new SubResult();
    }
}

class Result{}
class SubResult extends Result {}