package validation;

import javax.validation.constraints.NotNull;

public class UnderValidation {
    static final String EMPTY_NAME_MESSAGE = "Имя пустое";
    @NotNull(message = EMPTY_NAME_MESSAGE)
    String name;
}