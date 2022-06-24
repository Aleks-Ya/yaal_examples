package exercises;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

/**
 * Сумма цифр числа.
 * Во входном файле записано натуральное число не превышающее 10^100.
 * Требуется найти сумму цифр числа.
 */
public class SumOfNumbers {
    @Test
    void test() throws IOException {
        int degree = 100;
        Path path = Files.createTempFile("SumOfNumbers", ".tmp");
        OutputStream out = new FileOutputStream(path.toFile());
        Random r = new Random();
        for (int i = 0; i < degree; i++) {
            out.write(r.nextInt(10));
        }
        out.close();
        System.out.println("File content: \n" + Files.readAllLines(path));

        //todo нет стандартных stream из файла
    }
}
