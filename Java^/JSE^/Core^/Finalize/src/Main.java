import java.io.PrintStream;

public class Main {
    public static void main(String[] args) {
      new Main();//finalize будет вызван во время работы сборщика мусора
      System.gc();
      new Main();//finalize не будет вызван
    }
    
    @Override
    public void finalize() {
      System.out.println("FINALIZE " + this);
    }
}