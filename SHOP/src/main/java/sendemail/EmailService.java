package sendemail;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailService {

    public void enviarCorreo(String destinatario, String asunto, String cuerpo) throws MessagingException {
        //evita el error de String.length()
        if (destinatario == null || asunto == null || cuerpo == null) {
            throw new IllegalArgumentException("Uno de los campos del correo es null");
        }

        if (destinatario.isBlank() || asunto.isBlank() || cuerpo.isBlank()) {
            throw new IllegalArgumentException("Uno de los campos del correo está vacío");
        }

        // Configuración SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Autenticación
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                // Usa tus credenciales de Gmail o variables de entorno
                return new PasswordAuthentication("juanpa.acevedo.osorio.2006@gmail.com", "gfki jmyz goxq yixb");
            }
        });

        // Construcción del mensaje
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("juanpa.acevedo.osorio.2006@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject(asunto);
        message.setText(cuerpo);

        // Envío
        Transport.send(message);
    }
}
