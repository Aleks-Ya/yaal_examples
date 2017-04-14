package swing.Event.making;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Создание слушателей с помощью фабрики.
 * Пример из книги И. Портянкина "Swing. Эффектные пользовательские интерфейсы".
 */
public class FactoryEvents extends JFrame {
    // ссылка на нашу фабрику
    private ListenerFactory factory = new ListenerFactory();

    public FactoryEvents() {
        super("FactoryEvents");
        // событие при закрытии окна получаем от фабрики
        addWindowListener(factory.getWindowL());
        // добавим кнопку
        JButton button = new JButton("Нажмите меня");
        getContentPane().add(button);
        // слушатель кнопки также создается фабрикой
        button.addActionListener(factory.getButtonL());
        // выводим окно на экран
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new FactoryEvents();
    }
}

// фабрика классов
class ListenerFactory {
    // этот метод создает слушателя для кнопки
    public ActionListener getButtonL() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("ButtonListener");
            }
        };
    }

    // слушатель оконных событий
    public WindowListener getWindowL() {
        return new WindowL();
    }

    class WindowL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }
}