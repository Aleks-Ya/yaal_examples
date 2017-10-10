package swing.Container.JComponent.custom_component;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

/**
 * Пользовательский компонент.
 */
class CustomComponent {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Frame();
            }
        });
    }

    static class Frame extends JFrame {
        private static final int FRAME_WIDTH = 600;
        private static final int FRAME_HEIGHT = 400;

        Frame() {
            init();
            add(new MyComponent());
        }

        private void init() {
            setSize(FRAME_WIDTH, FRAME_HEIGHT);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setVisible(true);
            setLayout(new FlowLayout());
        }
    }
}

class MyComponent extends JComponent {
    public MyComponent() {
        setLayout(new BorderLayout());
        setSize(50, 100);

        Map<TextAttribute, Object> fontAttrs = new HashMap<>();
        fontAttrs.put(TextAttribute.FONT, TextAttribute.WEIGHT_HEAVY);
        fontAttrs.put(TextAttribute.SIZE, 20);
        fontAttrs.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        Font font = new Font(fontAttrs);

        final JTextField textField = new JTextField("День", 10);
        textField.setFont(font);
        final JLabel label = new JLabel("Ночь");

        add(textField, BorderLayout.SOUTH);
        add(label, BorderLayout.NORTH);
    }
}