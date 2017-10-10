package lang.generics;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Присваивание значений обобщенным переменным.
 */
public class Assignment {
    @Test
    public void testName() {
        List<? extends Collection> collections = new ArrayList<>();
        List<Set> sets = new ArrayList<>();
        collections = sets;
    }
}
