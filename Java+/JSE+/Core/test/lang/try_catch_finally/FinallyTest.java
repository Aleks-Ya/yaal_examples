package lang.try_catch_finally;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FinallyTest {

    /**
     * Если catch и finally бросают исключения, вверх по стеку полетит исключение из finally.
     */
    @Test
    void exceptionInCatchVsFinally() {
        var finallyMessage = "From finally";
        var e = assertThatThrownBy(() -> {
            //noinspection finally
            try {
                throw new RuntimeException("From try");
            } catch (Exception e1) {
                throw new RuntimeException("From catch");
            } finally {
                //noinspection ThrowFromFinallyBlock
                throw new RuntimeException(finallyMessage);
            }
        }).isInstanceOf(RuntimeException.class).hasMessage(finallyMessage);
    }

    /**
     * Если catch и finally возвращают значения, возвращено будет значение из finally.
     */
    @Test
    void returnFromCatchVsFinally() {
        var finallyMessage = "From finally";
        assertThat(returnString(finallyMessage)).isEqualTo(finallyMessage);
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
