package generics.inheritance;

import org.junit.Test;

/**
 * Иерархия классов (entity) и параллельная иерархия идентификаторов этих классов (id).
 */
public class Identifiers {
    @Test
    public void testName() {
        Entity apple = new AppleEntity();
        Id appleId = apple.id;

        Entity grape = new GrapeEntity();
        Id grapeId = grape.id;
    }
}
