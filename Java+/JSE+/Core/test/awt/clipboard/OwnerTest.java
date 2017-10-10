package awt.clipboard;

import org.junit.Test;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;

import static org.mockito.Mockito.*;

public class OwnerTest {
    @Test
    public void testLostOwnership() throws Exception {
        Owner owner = spy(new Owner());
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t = mock(Transferable.class);
        ClipboardOwner newOwner = mock(ClipboardOwner.class);
        cb.setContents(t, newOwner);
        verify(owner).lostOwnership(cb, t);
    }
}
