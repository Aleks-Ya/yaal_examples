package ru.yaal.example.java.se.gui.swing.gridlayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class Main {
    public static void main(String[] args) {
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

        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(text, BorderLayout.NORTH);
        frame.add(gridPanel, BorderLayout.CENTER);
    }
}
