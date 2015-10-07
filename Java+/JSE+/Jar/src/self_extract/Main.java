package self_extract;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Чтение файла из самораспаковывающегося (SFX) архива.
 * Не работает.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        InputStream is = Main.class.getResourceAsStream("lenta.exe");
        WinZipInputStream wzis = new WinZipInputStream(is);
        ZipInputStream zip = new ZipInputStream(wzis);
        ZipEntry entry;
        while ((entry = zip.getNextEntry()) != null) {
            System.out.println(entry.getName());
            Scanner in = new Scanner(zip);
            while (in.hasNextLine()) {
                System.out.println(in.nextLine());
            }
            zip.closeEntry();
        }
        zip.close();
    }
}