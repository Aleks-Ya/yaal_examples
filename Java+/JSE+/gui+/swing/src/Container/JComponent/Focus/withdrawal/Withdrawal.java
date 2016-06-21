package Container.JComponent.Focus.withdrawal;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Withdrawal {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WithdrawalFrame();
            }
        });
    }
}

class WithdrawalFrame extends JFrame {
    public WithdrawalFrame() {
        JPanel textFieldsPanel = new JPanel(new GridLayout(3, 3, 30, 30));

        final JComponent c00 = new JTextField("1");
        final JComponent c01 = new JTextField("2");
        final JComponent c02 = new JTextField("3");
        final JComponent c10 = new JTextField("4");
        final JComponent c11 = new JTextField("5");
        final JComponent c12 = new JPanel();
        final JComponent c20 = new JTextField("7");
        final JComponent c21 = new JTextField("8");
        final JComponent c22 = new JTextField("9");

        textFieldsPanel.add(c00);
        textFieldsPanel.add(c01);
        textFieldsPanel.add(c02);
        textFieldsPanel.add(c10);
        textFieldsPanel.add(c11);
        textFieldsPanel.add(c12);
        textFieldsPanel.add(c20);
        textFieldsPanel.add(c21);
        textFieldsPanel.add(c22);

        Border selectedBorder = BorderFactory.createLineBorder(Color.RED, 5);
        Border unSelectedBorder = c00.getBorder();
        c00.setBorder(selectedBorder);

        final JButton bOk = new JButton("OK");
        final JButton bCancel = new JButton("Cancel");

        Focus<JComponent> focus = new Focus<>(new JComponent[][]{
                {c00, c01, c02},
                {c10, c11, null},
                {c20, c21, c22},
                {bOk, bOk, bCancel}
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(bOk);
        buttonsPanel.add(bCancel);

        add(textFieldsPanel);
        add(buttonsPanel, BorderLayout.SOUTH);

        setSize(500, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        addWindowFocusListener(new FocusReturn(this));

        addKeyListener(new NarrowKeyListener(focus, selectedBorder, unSelectedBorder));
    }
}

class FocusReturn extends WindowAdapter {
    private Container focusHolder;

    FocusReturn(Container focusHolder) {
        this.focusHolder = focusHolder;
    }

    public void windowGainedFocus(WindowEvent e) {
        focusHolder.requestFocusInWindow();
    }
}

class NarrowKeyListener extends KeyAdapter {
    private Focus<JComponent> focus;
    private final Border selectedBorder;
    private final Border unSelectedBorder;

    NarrowKeyListener(Focus<JComponent> focus, Border selectedBorder, Border unSelectedBorder) {
        this.focus = focus;
        this.selectedBorder = selectedBorder;
        this.unSelectedBorder = unSelectedBorder;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN: {
                focus.down();
                focus();
                break;
            }
            case KeyEvent.VK_UP: {
                focus.up();
                focus();
                break;
            }
            case KeyEvent.VK_RIGHT: {
                focus.right();
                focus();
                break;
            }
            case KeyEvent.VK_LEFT: {
                focus.left();
                focus();
                break;
            }
        }
    }

    private void focus() {
        focus.prevSelected().setBorder(unSelectedBorder);
        focus.selected().setBorder(selectedBorder);
    }
}