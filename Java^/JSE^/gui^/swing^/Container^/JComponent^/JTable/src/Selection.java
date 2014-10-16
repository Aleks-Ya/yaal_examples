import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Выбор строк, столбцов, ячеек.
 */
public class Selection {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SelectionFrame();
            }
        });
    }
}

class SelectionFrame extends JFrame {
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 400;
    private static final String ROW_SELECTION = "Строки";
    private static final String COLUMN_SELECTION = "Столбцы";
    private static final String CELL_SELECTION = "Ячейки";

    SelectionFrame() {
        initFrame();

        //Данные
        Object[][] cells = {
                {"Меркурий", 2440.0, 0, false},
                {"Венера", 6052.0, 0, false},
                {"Земля", 6378.0, 1, false},
                {"Марс", 3397.0, 2, false},
                {"Юритер", 71492.0, 67, true},
                {"Сатурн", 60268.0, 18, true},
                {"Уран", 25559.0, 17, true},
                {"Нептун", 24766.0, 13, true}
        };

        //Заголовки столбцов
        Object[] headers = {"Планета", "Радиус", "Спутники", "Газовая?"};

        JTable table = new JTable(cells, headers);

        JScrollPane pane = new JScrollPane(table);

        JPanel cbPanel = initComboBox(table);

        add(pane, BorderLayout.CENTER);
        add(cbPanel, BorderLayout.NORTH);
    }

    private JPanel initComboBox(JTable table) {
        JComboBox<String> cbSelection = new JComboBox<>();
        cbSelection.addItem(ROW_SELECTION);
        cbSelection.addItem(COLUMN_SELECTION);
        cbSelection.addItem(CELL_SELECTION);
        cbSelection.addActionListener(new SelectionActionListener(table));

        JPanel selectionPanel = new JPanel();
        selectionPanel.add(cbSelection);
        return selectionPanel;
    }

    private void initFrame() {
        setTitle("Selection");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    class SelectionActionListener implements ActionListener {
        private JTable table;

        SelectionActionListener(JTable table) {
            this.table = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<String> source = (JComboBox<String>) e.getSource();
            String selectedItem = (String) source.getSelectedItem();
            switch (selectedItem) {
                case ROW_SELECTION: {
                    table.setColumnSelectionAllowed(false);
                    table.setCellSelectionEnabled(false);
                    table.setRowSelectionAllowed(true);
                    break;
                }
                case COLUMN_SELECTION: {
                    table.setRowSelectionAllowed(false);
                    table.setCellSelectionEnabled(false);
                    table.setColumnSelectionAllowed(true);
                    break;
                }
                case CELL_SELECTION: {
                    table.setCellSelectionEnabled(true);
                    break;
                }
                default: {
                    throw new RuntimeException();
                }
            }

        }
    }
}