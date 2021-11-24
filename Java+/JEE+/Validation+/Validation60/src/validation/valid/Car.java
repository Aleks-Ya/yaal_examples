package validation.valid;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

class Car {
    @NotEmpty
    public final String model;

    @NotNull
    public final Integer year;

    Car(String model, Integer year) {
        this.model = model;
        this.year = year;
    }
}
