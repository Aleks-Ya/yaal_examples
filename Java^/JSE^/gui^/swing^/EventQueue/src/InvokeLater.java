import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Использование EventQueue#invokeLater() для исполнения кода в потоке диспетчера событий.
 */
public class InvokeLater {
    public static void main(String[] args) throws InterruptedException {
        assert EventQueue.isDispatchThread();

        final JButton button = new JButton();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JFrame frame = new JFrame();
        frame.setSize(200, 50);
        frame.setVisible(true);
        frame.add(button);

        assert EventQueue.isDispatchThread();

        new Thread(new Changer(button)).start();
    }
}

class Changer implements Runnable {
    private final JButton button;
    private final Random random = new Random();

    Changer(JButton button) {
        this.button = button;
    }

    @Override
    public void run() {
        while (true) {
            final int nextInt = longTimeGeneration();
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    assert !EventQueue.isDispatchThread();
                    button.setText(String.valueOf(nextInt));
                }
            });
        }
    }

    private int longTimeGeneration() {
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return random.nextInt();
    }
}