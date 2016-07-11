package try_catch_finally;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class FinallyTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Если catch и finally бросают исключения, вверх по стеку полетит исключение из finally.
     */
    @Test
    public void exceptionInCatchVsFinally() {
        String finallyMessage = "From finally";
        exception.expectMessage(finallyMessage);
        //noinspection finally
        try {
            throw new RuntimeException("From try");
        } catch (Exception e) {
            throw new RuntimeException("From catch");
        } finally {
            //noinspection ThrowFromFinallyBlock
            throw new RuntimeException(finallyMessage);
        }
    }

    /**
     * Если catch и finally возвращают значения, возвращено будет значение из finally.
     */
    @Test
    public void returnFromCatchVsFinally() {
        String finallyMessage = "From finally";
        assertThat(returnString(finallyMessage), equalTo(finallyMessage));
    }

    private String returnString(String finallyMessage) {
        //noinspection finally
        try {
            throw new RuntimeException();
        } catch (Exception e) {
            return "from catch";
        } finally {
            //noinspection ReturnInsideFinallyBlock
            return finallyMessage;
        }
    }
}
