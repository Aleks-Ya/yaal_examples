package core.lang.finalize;

public class Finalize {
    public static void main(String[] args) {
        new Finalize();//finalize будет вызван во время работы сборщика мусора
        System.gc();
        new Finalize();//finalize не будет вызван
    }

    @Override
    public void finalize() {
        System.out.println("FINALIZE " + this);
    }
}