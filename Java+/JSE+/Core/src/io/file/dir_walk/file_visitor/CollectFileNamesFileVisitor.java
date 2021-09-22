package io.file.dir_walk.file_visitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Составляет список имен файлов.
 */
public class CollectFileNamesFileVisitor extends SimpleFileVisitor<Path> {
    private final List<String> fileNames = new ArrayList<>();

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        System.out.println("Pre visit: " + dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        fileNames.add(file.getFileName().toString());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        System.out.println("Post visit: " + dir);
        return FileVisitResult.CONTINUE;
    }

    public List<String> getFileNames() {
        return fileNames;
    }
}
