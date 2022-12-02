package swing.Container.Layout.BoxLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Container;
import java.awt.Dimension;

/**
 * Вертикальное расположение компонентов с помощью BoxLayout.
 */
public class VerticalBoxLayout {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();
        BoxLayout layout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);
        contentPane.setLayout(layout);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(textArea());
        frame.add(textField());
        frame.add(label());
        frame.setVisible(true);
    }

    private static JLabel label() {
        return new JLabel("Я помню чудное мгновенье");
    }

    private static JTextField textField() {
        JTextField text = new JTextField("Введите что-нибудь :)");
        text.setMaximumSize(new Dimension(250, 20));
        return text;
    }

    private static JTextArea textArea() {
        JTextArea textArea = new JTextArea("16.00 ЧУЖОЙ\n" +
                "Режиссер: Ридли Скотт, 1979, 117 мин.\n" +
                "В ролях: Сигурни Уивер, Том Скеррит, Иэн Холм, Джон Хёрт, Гарри Дин Стэнтон, Вероника Картрайт\n" +
                "Перевод: русские субтитры, английкий язык");
        textArea.setEditable(false);
        textArea.setMaximumSize(new Dimension(400, 200));
        return textArea;
    }
}
