package lang.reflection.field;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GetFieldTest {
    private static final FieldsClass OBJECT = new FieldsClass();
    private static final Class<FieldsClass> clazz = FieldsClass.class;

    @Test
    void getPublicInstanceField() throws IllegalAccessException, NoSuchFieldException {
        var field = clazz.getField("publicInstanceField");
        var value = (String) field.get(OBJECT);
        assertThat(value).isEqualTo("a");
    }

    @Test
    void getPublicStaticField() throws IllegalAccessException, NoSuchFieldException {
        var field = clazz.getField("publicStaticField");
        var value = (Double) field.get(null);
        assertThat(value).isEqualTo(2D);
    }

    @Test
    void getPrivateInstanceField() throws IllegalAccessException, NoSuchFieldException {
        var field = clazz.getDeclaredField("privateInstanceField");
        field.setAccessible(true);
        var value = (Integer) field.get(OBJECT);
        assertThat(value).isEqualTo(1);
    }

    @Test
    void getPrivateStaticField() throws IllegalAccessException, NoSuchFieldException {
        var field = clazz.getDeclaredField("privateStaticField");
        field.setAccessible(true);
        var value = (Boolean) field.get(null);
        assertThat(value).isEqualTo(true);
    }

    private static class FieldsClass {
        public static final Double publicStaticField = 2D;
        private static final Boolean privateStaticField = true;
        public final String publicInstanceField = "a";
        private final Integer privateInstanceField = 1;
    }
}
