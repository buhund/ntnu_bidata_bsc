package no.ntnu.idatt2106.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Handles sending emails to user through spring frameworks mail sender.
 * Currently used for sending "forgot password" emails.
 */
@Service
public class JavaMailSenderService {

    private final JavaMailSender mailSender;

    /**
     * Constructor for JavaMailSenderService
     *
     * @param mailSender the JavaMailSender instance to be used for sending emails
     */
    public JavaMailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Base method for sending emails.
     * The parameters can be modified based on what content the email should have and to what email it should be sent.
     *
     * @param to email to send to
     * @param subject subject of the email
     * @param text text content of the email
     */
    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("info.sparesti@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    /**
     * Method that use the sendSimpleEmail method to send an email containing a new generated account password.
     *
     * @param to email to send to
     * @param password new generated password
     */
    public void sendNewPasswordEmail(String to, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("info.sparesti@gmail.com");
        message.setTo(to);
        message.setSubject("Nytt passord til Sparesti");
        message.setText("Hei.\n\n"+
            "Ditt nye passord er: " + password + " . Vennligst endre passord etter første innlogging.\n\n" +
            "Hilsen alle oss i Sparesti");
        mailSender.send(message);
    }
}