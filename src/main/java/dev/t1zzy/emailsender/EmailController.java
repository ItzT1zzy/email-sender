package dev.t1zzy.emailsender;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailController {
    @FXML
    public Label passwordLabel;

    @FXML
    private TextField toField;

    @FXML
    private TextField subjectField;

    @FXML
    private TextArea bodyField;

    @FXML
    private Label statusLabel;

    @FXML
    protected void sendEmail() {
        Dotenv dotenv = Dotenv.load();

        String to = toField.getText();
        String from = "emailsenderbeta1337@gmail.com";
        String password = dotenv.get("PASSWORD");
        String subject = subjectField.getText();
        String body = bodyField.getText();

        sendEmail(from, password, to, subject, body);
    }

    private void sendEmail(String from, String password, String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            statusLabel.setText("Письмо успешно отправленно!");
            statusLabel.setTextFill(Color.GREEN);
        } catch (MessagingException e) {
            statusLabel.setText("Не удалось отправить письмо!");
            statusLabel.setTextFill(Color.RED);
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}
