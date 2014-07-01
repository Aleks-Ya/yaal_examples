package ru.yaal.example.java.jse.swing.keyboard.events;

import javax.swing.JLabel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class KeyboardEventsListener implements KeyListener {
    private JLabel label;

    KeyboardEventsListener(JLabel label) {
        this.label = label;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        String key;
        switch (e.getKeyChar()) {
            case KeyEvent.VK_ESCAPE: {
                key = "ESC";
                break;
            }
            case KeyEvent.VK_ENTER: {
                key = "Enter";
                break;
            }
            default: {
                key = String.valueOf(e.getKeyChar());
            }
        }

        int modifiers = e.getModifiers();
        String modifiersText = KeyEvent.getKeyModifiersText(modifiers);

        String oldText = label.getText();
        label.setText(oldText + modifiersText + key + " ");
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
