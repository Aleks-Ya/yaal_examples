import org.junit.Test;
import static java.lang.System.out;

public class LinkageTest {

	@Test
    public void main(){
        Parent obj = new Child();
        Tester t = new Tester();
        t.test(obj);
    }
}

class Tester{

    public void test(Parent obj){
        System.out.println("Testing parent...");
        obj.test();
    }

    public void test(Child obj){
        System.out.println("Testing child...");
        obj.test();
    }
}


class Parent{
    public void test(){
        out.println("parent::test");
    }
}

class Child extends Parent{
    public void test(){
        out.println("child::test");
    }
}