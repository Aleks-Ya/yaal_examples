package mockito.core.session;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.misusing.UnnecessaryStubbingException;
import util.ReflectionUtil;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.quality.Strictness.STRICT_STUBS;

class MockitoSessionTest {
    @Mock
    private File file;

    @Test
    void initializeAnnotatedFields() {
        var mockito = Mockito.mockitoSession().initMocks(this).startMocking();
        when(file.canExecute()).thenReturn(true);
        assertThat(file.canExecute()).isTrue();
        mockito.finishMocking();
    }

    @Test
    void testUnnecessaryStubbingException_field() {
        var mockito = Mockito.mockitoSession().initMocks(this).startMocking();
        when(file.canExecute()).thenReturn(true);
        assertThatThrownBy(mockito::finishMocking).isInstanceOf(UnnecessaryStubbingException.class);
    }

    @Test
    void testUnnecessaryStubbingException_local() {
        var mockito = Mockito.mockitoSession().startMocking();
        var file = Mockito.mock(File.class);
        when(file.canExecute()).thenReturn(true);
        assertThatThrownBy(mockito::finishMocking).isInstanceOf(UnnecessaryStubbingException.class);
    }

    @Test
    void defaultStrictness() {
        var mockito = Mockito.mockitoSession().startMocking();
        var listener = ReflectionUtil.readFieldValue(mockito, "listener");
        var strictness = ReflectionUtil.readFieldValue(listener, "currentStrictness");
        assertThat(strictness).isEqualTo(STRICT_STUBS);
        mockito.finishMocking();
    }
}
