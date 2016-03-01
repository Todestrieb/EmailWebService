package gob.jalisco.dtf.mail;

import java.util.Properties;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
//import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

/**
 *
 * @author ssocial_desarrollo
 */
@WebService(serviceName = "MailService",
        targetNamespace = "http://emailwebservice.com/")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
//@Stateless()
public class MailService {
    
    
    @WebMethod(operationName = "EnviarCorreo")
    public void EnviarMail(
                           @WebParam(name = "Remitente") final String Remitente,
                           @WebParam(name = "Contrasena") final String Password,
                           @WebParam(name = "CuerpoMensaje") String Mensaje,
                           @WebParam(name = "Destinatario") String Destinatario,
                           @WebParam(name = "Asunto") String Asunto
                           ) 
        {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Remitente, Password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Remitente));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(Destinatario));
            message.setSubject(Asunto);
            message.setText(Mensaje);

            Transport.send(message);
            System.out.println("Mensaje enviado");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
