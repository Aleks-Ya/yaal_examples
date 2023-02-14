package jpa.hibernate.entity.enumeration;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

enum Occupation5 {
    DEVELOPER("D"), MANAGER("M");
    private final String code;

    Occupation5(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Converter(autoApply = true)
    static class Occupation5Converter implements AttributeConverter<Occupation5, String> {
        public static boolean wasConvertToDatabaseColumnExecuted = false;
        public static boolean wasConvertToEntityAttributeExecuted = false;

        @Override
        public String convertToDatabaseColumn(Occupation5 occupation) {
            wasConvertToDatabaseColumnExecuted = true;
            if (occupation == null) {
                return null;
            }
            return occupation.getCode();
        }

        @Override
        public Occupation5 convertToEntityAttribute(String code) {
            wasConvertToEntityAttributeExecuted = true;
            if (code == null) {
                return null;
            }
            return Stream.of(values())
                    .filter(c -> c.getCode().equals(code))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
    }
}
