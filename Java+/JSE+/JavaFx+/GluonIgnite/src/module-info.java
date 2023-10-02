module Java.JSE.GluonIgnite.main {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javax.inject;
    requires com.google.guice;
    requires ignite.guice;
    exports ignite;
    opens ignite to com.google.guice, javafx.fxml;
}
