package awt.clipboard;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.BufferedReader;
import java.io.IOException;

class Owner extends Thread implements ClipboardOwner {
    public static void main(String[] args) {
        Owner owner = new Owner();
        owner.setName("Owner");
        owner.start();
    }

    @Override
    public void run() {
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        regainOwnership(sysClip);
        System.out.println("Listening to board...");
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void lostOwnership(Clipboard cb, Transferable t) {
        while (true) {
            try {
                Transferable contents = cb.getContents(null);
                processContents(contents);
                regainOwnership(cb);
                return;
            } catch (IllegalStateException e) {
                System.out.println("IllegalStateException");
                try {
                    Thread.sleep(100);//Когда копируем изображение, буфер бывает занят и падает IllegalStateException (ждем, пока буфер освободится)
                } catch (InterruptedException ignore) {
                }
            }
        }
    }

    private void processContents(Transferable t) {
        DataFlavor expectedFlavor = DataFlavor.stringFlavor;
        if (t.isDataFlavorSupported(expectedFlavor)) {
            try {
                System.out.println("Processing: " + t);
                BufferedReader r = new BufferedReader(expectedFlavor.getReaderForText(t));
                String line;
                while ((line = r.readLine()) != null) {
                    System.out.println("Line: " + line);
                }
                r.close();
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Unsupported Data Flavor");
        }
    }

    private void regainOwnership(Clipboard cb) {
        cb.setContents(cb.getContents(null), this);
    }
}