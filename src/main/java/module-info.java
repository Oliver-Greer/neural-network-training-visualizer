module edu.srjc.a14.greer.oliver.final_greer_oliver {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.srjc.a14.greer.oliver.final_greer_oliver to javafx.fxml;
    exports edu.srjc.a14.greer.oliver.final_greer_oliver;
    exports edu.srjc.a14.greer.oliver.final_greer_oliver.DataHandling;
    opens edu.srjc.a14.greer.oliver.final_greer_oliver.DataHandling to javafx.fxml;
    exports edu.srjc.a14.greer.oliver.final_greer_oliver.Objects3D;
    opens edu.srjc.a14.greer.oliver.final_greer_oliver.Objects3D to javafx.fxml;
}