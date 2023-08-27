package javafx.binding;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.Test;

class PropertiesBindingTest {
    @Test
    void bindStringWithConversion() {
        StringProperty propertyA = new SimpleStringProperty();
        StringProperty propertyB = new SimpleStringProperty();

        // Create a binding such that propertyB will have the reversed value of propertyA
        propertyB.bind(Bindings.createStringBinding(() -> {
            if (propertyA.get() != null) {
                return new StringBuilder(propertyA.get()).reverse().toString();
            }
            return "";
        }, propertyA));

        // Test the binding
        propertyA.set("hello");
        System.out.println("propertyA: " + propertyA.get());  // Outputs: hello
        System.out.println("propertyB: " + propertyB.get());  // Outputs: olleh

        propertyA.set("world");
        System.out.println("propertyA: " + propertyA.get());  // Outputs: world
        System.out.println("propertyB: " + propertyB.get());  // Outputs: dlrow
    }
}
