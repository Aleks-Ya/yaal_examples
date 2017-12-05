package mockito.mock.verify;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Capture arguments that was passed to a mock.
 */
public class ArgumentCaptorTest {

    @Test
    public void singleValue() {
        ArgumentCaptor<Date> captor = ArgumentCaptor.forClass(Date.class);
        Date mock = mock(Date.class);

        Date expDate = new Date();

        mock.after(expDate);

        verify(mock, times(1)).after(captor.capture());

        Date actDate = captor.getValue();
        assertThat(actDate, equalTo(expDate));
    }


    @Test
    public void multiValues() {
        ArgumentCaptor<Date> captor = ArgumentCaptor.forClass(Date.class);
        Date mock = mock(Date.class);

        Date expDate1 = new Date(1L);
        Date expDate2 = new Date(2L);

        mock.after(expDate1);
        mock.after(expDate2);

        verify(mock, times(2)).after(captor.capture());

        List<Date> actDates = captor.getAllValues();
        assertThat(actDates, contains(expDate1, expDate2));
    }
}
