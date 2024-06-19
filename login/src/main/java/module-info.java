module com.mycompany.login {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.mycompany.login to javafx.fxml;
    exports com.mycompany.login;
    requires com.jfoenix;
    requires org.controlsfx.controls;
}
