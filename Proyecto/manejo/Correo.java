package main.java.com.Proyecto.manejo;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Correo {

    /**
     * Correo del remitente
     */
    private static final String CORREO = "decma64@gmail.com";

    /**
     * Contraseña del remitente
     */
    private static final String CONTRASENA = "otor nfkg pnbt ndfe";

    /**
     * Enviar un correo electrónico
     * @param destinatario Correo del destinatario
     * @param asunto Asunto del correo
     * @param cuerpo Cuerpo del correo
     * @return true si el correo se envió con éxito, false en caso contrario
     */
    public static boolean enviar(String destinatario, String asunto, String cuerpo) {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(CORREO, CONTRASENA);
                    }
                });
        
        try {
            // Crear un mensaje de correo electrónico
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(CORREO));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(destinatario)
            );
            message.setSubject(asunto);
            message.setText(cuerpo);
            
            // Enviar el correo
            Transport.send(message);

            return true;
            
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }
}