module me.clndr.tkvm {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires jlayer;


    opens me.clndr.tkvm to javafx.fxml;
    exports me.clndr.tkvm;
}