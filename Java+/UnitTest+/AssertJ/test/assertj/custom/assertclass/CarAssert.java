package assertj.custom.assertclass;

import org.assertj.core.api.AbstractAssert;

public class CarAssert extends AbstractAssert<CarAssert, Car> {

    private CarAssert(Car actual) {
        super(actual, CarAssert.class);
    }

    public static CarAssert assertThat(Car actual) {
        return new CarAssert(actual);
    }

    @SuppressWarnings("UnusedReturnValue")
    public CarAssert hasModel(String model) {
        isNotNull();
        objects.assertEqual(info, actual.model(), model);
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public CarAssert hasYear(int year) {
        isNotNull();
        objects.assertEqual(info, actual.year(), year);
        return this;
    }

}
