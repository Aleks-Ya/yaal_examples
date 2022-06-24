package ews;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.exception.service.remote.ServiceResponseException;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SendEmailTest {
    private static ExchangeService service;

    @BeforeAll
    public static void setUpService() throws Exception {
        String login = System.getenv("login");
        String password = System.getenv("password");
        String exchangeServerUrl = System.getenv("exchangeUrl");

        ExchangeCredentials credentials = new WebCredentials(login, password);
        service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        service.setCredentials(credentials);
        service.setUrl(new URI(exchangeServerUrl));
    }

    @Test
    void sendSuccess() throws Exception {
        String emailAddress = System.getenv("email");
        EmailMessage msg = new EmailMessage(service);
        msg.setSubject("Hello world!");
        msg.setBody(MessageBody.getMessageBodyFromText("Sent using the EWS Java API."));
        msg.getToRecipients().add(emailAddress);
        msg.send();
    }

    @Test
    void sendError() {
        assertThrows(ServiceResponseException.class, () -> {
            String emailAddress = "not_exists";
            EmailMessage msg = new EmailMessage(service);
            msg.setSubject("Hello world!");
            msg.setBody(MessageBody.getMessageBodyFromText("Sent using the EWS Java API."));
            msg.getToRecipients().add(emailAddress);
            msg.send();
        });
    }
}