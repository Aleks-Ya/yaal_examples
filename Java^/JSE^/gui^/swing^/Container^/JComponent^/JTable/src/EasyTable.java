import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;

/**
 * Простая таблица.
 */
class EasyTable {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EasyTableFrame();
            }
        });
    }
}

class EasyTableFrame extends JFrame {
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 400;

    EasyTableFrame() {
        initFrame();

        //Данные
        Object[][] cells = {
                {"Меркурий", 2440.0, 0, false},
                {"Венера", 6052.0, 0, false},
                {"Земля", 6378.0, 1, false},
                {"Марс", 3397.0, 2, false},
                {"Юритер", 71492.0, 67, true},
                {"Сатурн", 60268.0, 18, true},
                {"Уран", 25559.0, 0, true},
                {"Нептун", 24766.0, 0, true}
        };

        //Заголовки столбцов
        Object[] headers = {"Планета", "Радиус", "Спутники", "Газовая?"};

        final JTable table = new JTable(cells, headers);

        //Включить сортировку по щелчку на заголовке столбцов
        table.setAutoCreateRowSorter(true);

        JScrollPane pane = new JScrollPane(table);

        JPanel pButton = initPrintButton(table);

        add(pane, BorderLayout.CENTER);
        add(pButton, BorderLayout.SOUTH);
    }

    private JPanel initPrintButton(final JTable table) {
        JButton print = new JButton("Распечатать");
        print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    //Печать таблицы на принтере
                    table.print();
                } catch (PrinterException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        });


        JPanel pButton = new JPanel();
        pButton.add(print);
        return pButton;
    }

    private void initFrame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}