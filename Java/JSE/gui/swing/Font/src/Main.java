import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JLabel label = new JLabel("Съешь этих французских булок да выпей чаю, мудак");

        JFrame frame = new JFrame();
        frame.setSize(600, 100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(label);
    }
}
