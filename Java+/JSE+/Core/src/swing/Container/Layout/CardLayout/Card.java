package swing.Container.Layout.CardLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Card {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BorderFrame();
            }
        });
    }
}

class BorderFrame extends JFrame implements ItemListener {
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 400;
    private static final String BUTTON_PANEL = "Button Panel";
    private static final String TEXT_FIELD_PANEL = "Text Field Panel";
    private JPanel cardPanel;

    BorderFrame() throws HeadlessException {
        init();

        JPanel compoBoxPanel = makeCompoBoxPanel();

        cardPanel = makeCardPanel();

        add(compoBoxPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel makeCardPanel() {
        JPanel card1 = new JPanel();
        card1.add(new JButton(BUTTON_PANEL));

        JPanel card2 = new JPanel();
        card2.add(new JTextField(TEXT_FIELD_PANEL));

        JPanel cardPanel = new JPanel(new CardLayout());
        cardPanel.add(card1, BUTTON_PANEL);
        cardPanel.add(card2, TEXT_FIELD_PANEL);
        return cardPanel;
    }

    private JPanel makeCompoBoxPanel() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setEditable(false);
        comboBox.addItem(BUTTON_PANEL);
        comboBox.addItem(TEXT_FIELD_PANEL);
        comboBox.addItemListener(this);

        JPanel pComboBox = new JPanel();
        pComboBox.add(comboBox);
        return pComboBox;
    }

    private void init() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        CardLayout cl = (CardLayout) (cardPanel.getLayout());
        cl.show(cardPanel, (String) e.getItem());
    }
}