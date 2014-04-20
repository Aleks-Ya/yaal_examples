package ru.yaal.practics.java.se.core.clipboard;

import java.awt.*;
import java.awt.datatransfer.*;

class Owner extends Thread implements ClipboardOwner {
    public Owner() {
        super("Owner");
        start();
    }

    @Override
    public void run() {
        regainOwnership(Toolkit.getDefaultToolkit().getSystemClipboard());
        while (!interrupted()) {
        }
    }

    @Override
    public void lostOwnership(Clipboard cb, Transferable t) {
        process(t);
        regainOwnership(cb);
    }

    private void process(Transferable t) {
        try {
        System.out.println("Processing: " + t.isDataFlavorSupported(DataFlavor.stringFlavor));
        } catch (IllegalStateException e) {

        }
    }

    private void regainOwnership(Clipboard cb) {
        cb.setContents(cb.getContents(null), this);
    }
}