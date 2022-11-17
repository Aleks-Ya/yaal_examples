package reactor2.exercises;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Random;

/**
 * Сумма цифр числа.
 * Во входном файле записано натуральное число не превышающее 10^100.
 * Требуется найти сумму цифр числа.
 */
class SumOfNumbersTest {
    @Test
    void test() throws IOException {
        var degree = 100;
        var path = Files.createTempFile("SumOfNumbers", ".tmp");
        OutputStream out = new FileOutputStream(path.toFile());
        var r = new Random();
        for (var i = 0; i < degree; i++) {
            out.write(r.nextInt(10));
        }
        out.close();
        System.out.println("File content: \n" + Files.readAllLines(path));

        //todo нет стандартных stream из файла
    }
}
