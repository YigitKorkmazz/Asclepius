module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires java.desktop;
    requires jdk.sctp;
    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}