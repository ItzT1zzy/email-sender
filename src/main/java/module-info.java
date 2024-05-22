module dev.t1zzy.emailtest {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.mail;
    requires java.dotenv;

    opens dev.t1zzy.emailsender to javafx.fxml;
    exports dev.t1zzy.emailsender;
}