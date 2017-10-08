package core.swing.Font;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class AvailableFonts {
    public static void main(String[] args) {
        Font[] availableFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        System.out.printf("Available fonts: %d\n", availableFonts.length);
    }
}
