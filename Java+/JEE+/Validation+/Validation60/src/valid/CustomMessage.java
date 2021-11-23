package valid;

import javax.validation.constraints.NotNull;

public class CustomMessage {
    static final String EMPTY_NAME_MESSAGE = "Имя пустое";
    @NotNull(message = EMPTY_NAME_MESSAGE)
    String name;
}
