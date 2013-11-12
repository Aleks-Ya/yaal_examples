package ru.yaal.practics.java.se.core.clipboard;

import org.testng.annotations.Test;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OwnerTest {
    @Test
    public void testLostOwnership() throws Exception {
        Owner owner = new Owner();
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t = mock(Transferable.class);
        ClipboardOwner newOwner = mock(ClipboardOwner.class);
        cb.setContents(t, newOwner);
        verify(t).isDataFlavorSupported(DataFlavor.stringFlavor);//test error: Wanted but not invoked
        owner.interrupt();
//        verify(owner).lostOwnership(cb, t);//test error: Wanted but not invoked
    }
}
