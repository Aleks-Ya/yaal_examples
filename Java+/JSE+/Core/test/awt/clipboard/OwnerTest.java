package awt.clipboard;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class OwnerTest {
    @Test
    @Disabled("Not work")
    void testLostOwnership() {
        var owner = spy(new Owner());
        var cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        var t = mock(Transferable.class);
        var newOwner = mock(ClipboardOwner.class);
        cb.setContents(t, newOwner);
        verify(owner).lostOwnership(cb, t);
    }
}
