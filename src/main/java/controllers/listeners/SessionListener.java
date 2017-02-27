package controllers.listeners;

import common.utils.EmailSender;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * Created by Mordr on 27.02.2017.
 */
@WebListener
public class SessionListener implements HttpSessionAttributeListener {
    private static final Logger logger = Logger.getLogger(SessionListener.class);
    private static final String ADMIN_ATTRIBUTE_NAME = "adminLoggedIn";

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        String attributeName = event.getName();
        Object attributeValue = event.getValue();
        if(ADMIN_ATTRIBUTE_NAME.equals(attributeName)) {
            Thread emailSendingThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    logger.trace("Admin logged in, sending email");
                    EmailSender emailSender = new EmailSender();
                    logger.trace("message text formatting");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Timestamp now = new Timestamp(System.currentTimeMillis());
                    String emailText = "Admin " + attributeValue + " logged in at " + dateFormat.format(now) + ".";
                    logger.trace("message formatted, got to email");
                    emailSender.sendEmail("Admin logged in", emailText, null);
                }
            });
            emailSendingThread.start();
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {

    }
}
