package storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;

public class WriteToVolumeAndWait {
    public static void main(String[] args) throws InterruptedException, IOException {
        Path volumeDir = Paths.get("/tmp/data");
        Files.createDirectories(volumeDir);
        Path dataFile = volumeDir.resolve("data.txt");
        if (Files.exists(dataFile)) {
            System.out.println("File is found:");
            Files.readAllLines(dataFile).forEach(System.out::println);
        } else {
            Files.createFile(dataFile);
        }

        System.out.println("Writing to file: " + dataFile);
        String expData = Instant.now().toString() + "\n";
        Files.write(dataFile, expData.getBytes(), StandardOpenOption.APPEND);
        System.out.println("Writing is done");

        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.println("Heartbeat");
            Thread.sleep(5000);
        }
    }
}
