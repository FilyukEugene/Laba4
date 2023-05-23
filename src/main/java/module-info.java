module com.example.laba4level1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.laba4level1 to javafx.fxml;
    exports com.example.laba4level1;
}