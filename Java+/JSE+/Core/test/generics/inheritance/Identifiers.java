package generics.inheritance;

import org.junit.Test;

/**
 * Иерархия классов (entity) и параллельная иерархия идентификаторов этих классов (id).
 */
public class Identifiers {
    @Test
    public void testName() {
        Entity<AppleId> apple = new AppleEntity();
        Id<AppleEntity> appleId = apple.id;

        Entity<GrapeId> grape = new GrapeEntity();
        Id<GrapeEntity> grapeId = grape.id;
    }
}
