package joplin;

import java.util.Objects;

public enum MarkupLanguage {
    HTML(2), MD(1);
    private final Integer code;

    MarkupLanguage(Integer code) {
        this.code = code;
    }

    public static MarkupLanguage parseCode(Integer code) {
        if (Objects.equals(code, MD.getCode())) {
            return MD;
        }
        if (Objects.equals(code, HTML.getCode())) {
            return HTML;
        }
        throw new IllegalArgumentException("Unsupported code: " + code);
    }

    public Integer getCode() {
        return code;
    }
}
