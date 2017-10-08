package core.swing.thread.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    Changer(JButton button) {
        this.button = button;
    }

    @Override
    public void run() {
        int second = 0;
        while (true) {
            EventQueue.invokeLater(new UpdateButtonText(second, button));
            waitASecond();
            second++;
        }
    }

    private void waitASecond() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class UpdateButtonText implements Runnable {
    private final String text;
    private final JButton button;

    UpdateButtonText(int text, JButton button) {
        assert EventQueue.isDispatchThread();
        this.button = button;
        this.text = String.valueOf(text);
    }

    @Override
    public void run() {
        assert !EventQueue.isDispatchThread();
        button.setText(text);
    }
}