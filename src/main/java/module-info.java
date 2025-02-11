module org.example.quadtree {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.quadtree to javafx.fxml;
    exports org.example.quadtree;
}