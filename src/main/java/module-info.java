module me.clndr.tkvm {
    requires javafx.controls;
    requires javafx.fxml;


    opens me.clndr.tkvm to javafx.fxml;
    exports me.clndr.tkvm;
}