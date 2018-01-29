package io.zip;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PackZipTest {

    @Test
    public void pack() throws IOException {
        File tempFile = File.createTempFile(getClass().getSimpleName(), ".zip");
        System.out.println("File: " + tempFile.getAbsolutePath());
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(tempFile))) {
            writeEntry(zos, "root.txt", "root");
            writeEntry(zos, "dir" + File.separator + "file1.txt", "abc");
            writeEntry(zos, "dir" + File.separator + "file2.txt", "dbe");
        }
    }

    private void writeEntry(ZipOutputStream zos, String entryName, String entryContent) throws IOException {
        ZipEntry rootEntry = new ZipEntry(entryName);
        zos.putNextEntry(rootEntry);
        zos.write(entryContent.getBytes());
        zos.closeEntry();
    }
}