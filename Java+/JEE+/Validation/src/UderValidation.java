import javax.validation.constraints.NotNull;

public class UderValidation {
    @NotNull(message = "Имя пустое")
    String name;
}
