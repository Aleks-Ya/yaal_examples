import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.BorderLayout;
import java.awt.EventQueue;

/**
 * Таблица на основе простой модели таблицы.
 */
public class CustomTableModel {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CustomTableModelFrame();
            }
        });
    }
}

class CustomTableModelFrame extends JFrame {
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 400;

    CustomTableModelFrame() {
        initFrame();

        TableModel model = new InvestmentModel(30, 10, 15);
        JTable table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);

        add(pane, BorderLayout.CENTER);
    }

    private void initFrame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

class InvestmentModel extends AbstractTableModel {
    private static final double INITIAL_BALANCE = 10000.0;
    private int years;
    private int minRate;
    private int maxRate;

    InvestmentModel(int y, int r1, int r2) {
        years = y;
        minRate = r1;
        maxRate = r2;
    }

    @Override
    public int getRowCount() {
        return years;
    }

    @Override
    public int getColumnCount() {
        return maxRate - minRate + 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        double rate = (columnIndex + minRate) / 100.0;
        int nPeriods = rowIndex;
        double futureBalance = INITIAL_BALANCE * Math.pow(1 + rate, nPeriods);
        return String.format("%.2f", futureBalance);
    }

    @Override
    public String getColumnName(int column) {
        return (column + minRate) + "%";
    }
}