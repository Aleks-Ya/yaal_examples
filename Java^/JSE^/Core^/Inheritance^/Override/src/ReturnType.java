package type;

/**
 * Переопределяющий метод может декларировать возвращаемый параметр дочернего класса.
 */
public class ReturnType {
    public static void main(String[] args) {
        Child child = new Child();
        Result r = child.makeResult();
        System.out.println(r.getClass());
        System.out.println(child.getNum());
    }
}

class Parent {
    Result makeResult() {
        return new Result();
    }
    
    long getNum() {
		return 1;
	}
}

class Child extends Parent {
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

class Result{}
class SubResult extends Result {}