package common.utils;

import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * Created by Mordr on 27.02.2017.
 * Отправка email
 */
public class EmailSender {
    private static final Logger logger = Logger.getLogger(EmailSender.class);
    private static final String EMAIL_SENDER_USERNAME = "innopolistest@gmail.com";
    private static final String EMAIL_SENDER_PASSWORD = "testinnopolis";
    private static final String SPAM_VICTIM = "a.kharkhanov.stc@innopolis.ru";
    private final Properties props;

    public EmailSender() {
        this.props = new Properties();
        this.props.put("mail.smtp.host", "smtp.gmail.com");
        this.props.put("mail.smtp.socketFactory.port", "465");
        this.props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        this.props.put("mail.smtp.auth", "true");
        this.props.put("mail.smtp.port", "465");
        logger.trace("Email sender created and its properties set");
    }

    public void sendEmail(String subject, String emailText, String fileName) {
        logger.trace("sending email began");
        Session session = Session.getDefaultInstance(this.props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL_SENDER_USERNAME, EMAIL_SENDER_PASSWORD);
                    }
                });

        try {
            logger.trace("message creating...");
            Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(SPAM_VICTIM));
            message.setSubject(subject);
            message.setText(emailText);

            logger.trace("checking if file need to be attached...");
            if(fileName != null) {
                logger.trace("attaching file to email...");
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                Multipart multipart = new MimeMultipart();
                DataSource source = new FileDataSource(fileName);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(fileName);
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
            }
            logger.trace("sending email...");
            Transport.send(message);
            logger.trace("Send email to " + SPAM_VICTIM);
        } catch (MessagingException e) {
            logger.error(e);
        }
    }
}
