package lang.generics.inheritance;

import org.junit.jupiter.api.Test;

/**
 * Иерархия классов (entity) и параллельная иерархия идентификаторов этих классов (id).
 */
class IdentifiersTest {
    @Test
    void testName() {
        Entity<AppleId> apple = new AppleEntity();
        Id<AppleEntity> appleId = apple.id;

        Entity<GrapeId> grape = new GrapeEntity();
        Id<GrapeEntity> grapeId = grape.id;
    }
}
