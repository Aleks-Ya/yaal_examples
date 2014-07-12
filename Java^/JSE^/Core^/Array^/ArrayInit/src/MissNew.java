import java.util.Arrays;
import static java.lang.System.out;

/**
 * Инициализация массива без new.
 */
public class MissNew {
    public static void main(String[] args) {
        {
        // В одну строку - ОК
        int intArray[] = {0, 1};
        String[] strArray = {"Summer", "Winter"};
        int multiArray[][] = { {0, 1}, {3, 4, 5} };
        }
        
        {
        // В две строки только с new, иначе ошибка компиляции
        int intArray[];
        intArray = new int[] {0, 1};
        
        String[] strArray; 
        strArray = new String[] {"Summer", "Winter"};
        
        int multiArray[][];
        multiArray = new int[][] { {0, 1}, {3, 4, 5} };
        }
    }
}