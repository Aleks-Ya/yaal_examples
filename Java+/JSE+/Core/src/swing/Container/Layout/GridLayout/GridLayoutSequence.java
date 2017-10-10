package swing.Container.Layout.GridLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Добавление компонентов по порядку.
 */
public class GridLayoutSequence {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GridLayoutSequenceFrame();
            }
        });
    }
}

class GridLayoutSequenceFrame extends JFrame {
    GridLayoutSequenceFrame() throws HeadlessException {
        JTextField text = new JTextField("Введите что-нибудь :)");

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(8, 2));
        gridPanel.add(new JLabel("Номер карты:"));
        gridPanel.add(new JLabel("1234 5678 9012"));

        gridPanel.add(new JLabel("Номинал:"));
        gridPanel.add(new JLabel("10 000 руб."));

        gridPanel.add(new JLabel("Статус:"));
        gridPanel.add(new JLabel("АКТИВНА"));

        gridPanel.add(new JLabel("Дата активации:"));
        gridPanel.add(new JLabel("10.03.2014"));

        gridPanel.add(new JLabel("Срок действия:"));
        gridPanel.add(new JLabel("09.03.2015"));

        gridPanel.add(new JLabel("Магазин:"));
        gridPanel.add(new JLabel("Савушкина, 122"));

        gridPanel.add(new JLabel("Касса:"));
        gridPanel.add(new JLabel("3"));

        gridPanel.add(new JButton("ЗАКРЫТЬ"));
        gridPanel.add(new JButton("ПЕЧАТЬ"));

        setLayout(new BorderLayout());
        setSize(500, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        add(text, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);
    }
}