package javafx.binding;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.Test;

class BindWithConversionTest {
    @Test
    void bindStringWithConversion() {
        StringProperty propertyA = new SimpleStringProperty();
        StringProperty propertyB = new SimpleStringProperty();

        propertyB.bind(Bindings.createStringBinding(() -> {
            if (propertyA.get() != null) {
                return new StringBuilder(propertyA.get()).reverse().toString();
            }
            return "";
        }, propertyA));

        propertyA.set("hello");
        System.out.println("propertyA: " + propertyA.get());
        System.out.println("propertyB: " + propertyB.get());

        propertyA.set("world");
        System.out.println("propertyA: " + propertyA.get());
        System.out.println("propertyB: " + propertyB.get());
    }
}
