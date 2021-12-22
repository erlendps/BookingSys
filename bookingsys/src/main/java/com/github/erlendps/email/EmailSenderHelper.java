package com.github.erlendps.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;
import io.github.cdimascio.dotenv.Dotenv;

public class EmailSenderHelper {

  private static JavaMailSender getJavaMailSender() {
    Dotenv dotenv = Dotenv.load();
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);

    mailSender.setUsername(dotenv.get("SMTP_USERNAME"));
    mailSender.setPassword(dotenv.get("SMTP_PASSWORD"));

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");

    return mailSender;
  }

  public static void sendSimpleMail(String to, String subject, String text) {
    JavaMailSender mailSender = getJavaMailSender();
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setFrom("noreply@bookingsys.com");
    msg.setTo(to);
    msg.setSubject(subject);
    msg.setText(text);
    mailSender.send(msg);
  }
}
