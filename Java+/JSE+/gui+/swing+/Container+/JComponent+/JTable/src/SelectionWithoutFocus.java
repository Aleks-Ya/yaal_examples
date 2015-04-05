import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

/**
 * Сделать курсор выделения невидимым.
 */
class SelectionWithoutFocus {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SelectionWithoutFocusFrame();
            }
        });
    }
}

class SelectionWithoutFocusFrame extends JFrame {
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 400;

    SelectionWithoutFocusFrame() {
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
        table.setDefaultRenderer(Object.class, new CellRenderer());

        final JLabel selectedRow = new JLabel("Выбрана строка: " + table.getSelectedRow());
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRow.setText(String.format("Выбраны строки: %d-%d", e.getFirstIndex(), e.getLastIndex()));
            }
        });

        JPanel labelPanel = new JPanel();
        labelPanel.add(selectedRow);

        JScrollPane tablePanel = new JScrollPane(table);

        add(tablePanel, BorderLayout.CENTER);
        add(labelPanel, BorderLayout.SOUTH);
    }

    private void initFrame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

}

class CellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return super.getTableCellRendererComponent(table, value, false, false, row, column);
    }
}
