package jpa.entity.enumeration;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
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
        @Override
        public String convertToDatabaseColumn(Occupation5 occupation) {
            if (occupation == null) {
                return null;
            }
            return occupation.getCode();
        }

        @Override
        public Occupation5 convertToEntityAttribute(String code) {
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
