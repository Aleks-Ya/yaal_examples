import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import java.awt.EventQueue;

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
        JScrollPane pane = initTable();
        add(pane);
    }

    private JScrollPane initTable() {
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

        Object[] headers = {"Планета", "Радиус", "Спутники", "Газовая?"};

        JTable table = new JTable(cells, headers);
        return new JScrollPane(table);
    }

    private void initFrame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}