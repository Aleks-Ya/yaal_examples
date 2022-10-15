package calc;

import org.junit.jupiter.api.Test;
import calc.Calculator;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
  @Test
  void testSum(){
      assertEquals(7, Calculator.sum(2, 5));
  }
}