package mockito.mock.verify;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Capture arguments that was passed to a mock.
 */
class ArgumentCaptorTest {

    @Test
    void singleValue() {
        var captor = ArgumentCaptor.forClass(Date.class);
        var mock = mock(Date.class);

        var expDate = new Date();

        mock.after(expDate);

        verify(mock, times(1)).after(captor.capture());

        var actDate = captor.getValue();
        assertThat(actDate, equalTo(expDate));
    }


    @Test
    void multiValues() {
        var captor = ArgumentCaptor.forClass(Date.class);
        var mock = mock(Date.class);

        var expDate1 = new Date(1L);
        var expDate2 = new Date(2L);

        mock.after(expDate1);
        mock.after(expDate2);

        verify(mock, times(2)).after(captor.capture());

        var actDates = captor.getAllValues();
        assertThat(actDates, contains(expDate1, expDate2));
    }
}
