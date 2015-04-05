import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.LayoutManager;

/**
 * Пример из книги "Swing. Эффективные пользовательские интерфейсы" (стр. 107)
 */
public class VerticalLayoutManager {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VerticalLayoutFrame();
            }
        });
    }
}

class VerticalLayout implements LayoutManager {

    // сигнал расположить компоненты в контейнере
    @Override
    public void layoutContainer(Container c) {
        Component comps[] = c.getComponents();
        int currentY = 5;
        for (Component comp : comps) {
            // предпочтительный размер компонента
            Dimension pref = comp.getPreferredSize();
            // указываем положение компонента на экране
            comp.setBounds(5, currentY, pref.width, pref.height);
            // промежуток в 5 пикселов
            currentY += 5;
            currentY += pref.height;
        }
    }

    // эти два метода нам не понадобятся
    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    // минимальный размер для контейнера
    @Override
    public Dimension minimumLayoutSize(Container c) {
        return calculateBestSize(c);
    }

    // предпочтительный размер для контейнера
    @Override
    public Dimension preferredLayoutSize(Container c) {
        return calculateBestSize(c);
    }

    private Dimension size = new Dimension();
    // вычисляет оптимальный размер контей

    // вычисляет оптимальный размер контейнера
    private Dimension calculateBestSize(Container c) {
        // сначала вычислим длину контейнера
        Component[] comps = c.getComponents();
        int maxWidth = 0;
        for (Component comp : comps) {
            int width = comp.getWidth();
            // поиск компонента с максимальной длиной
            if (width > maxWidth) maxWidth = width;
        }
        // длина контейнера с учетом левого отступа
        size.width = maxWidth + 5;
        // вычисляем высоту контейнера
        int height = 0;
        for (Component comp : comps) {
            height += 5;
            height += comp.getHeight();
        }
        size.height = height;
        return size;
    }
}

class VerticalLayoutFrame extends JFrame {
    VerticalLayoutFrame() throws HeadlessException {
        setSize(500, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        JPanel contents = new JPanel(new VerticalLayout());
        contents.add(new JButton("Один"));
        contents.add(new JButton("Два"));
        contents.add(new JTextField(30));
        setContentPane(contents);
    }
}