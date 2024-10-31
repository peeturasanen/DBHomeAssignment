module org.example.db_homeassignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.db_homeassignment to javafx.fxml;
    exports org.example.db_homeassignment;
}