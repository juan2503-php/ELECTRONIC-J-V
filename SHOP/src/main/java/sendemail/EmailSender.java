package sendemail;


import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import carrito.Carrito;
import tienda.Producto;

public class EmailSender {

    private final String smtpHost = "smtp.gmail.com";
    private final String smtpPort = "587"; // TLS
    private final String username;
    private final String password;

    public EmailSender(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Envía el carrito como HTML al correo destino.
     * Lanza MessagingException si falla el envío.
     */
    public void sendCartAsEmail(Carrito carrito, String toEmail, String subject) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.ssl.trust", smtpHost);

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject == null || subject.isBlank() ? "Resumen de tu carrito" : subject);

        // Construir HTML del carrito (formateo en es-CO)
        NumberFormat nf = NumberFormat.getInstance(new Locale("es", "CO"));
        StringBuilder html = new StringBuilder();
        html.append("<html><body>");
        html.append("<h2>Resumen del carrito</h2>");
        html.append("<table style='border-collapse:collapse;'>");
        html.append("<tr><th style='padding:6px;border:1px solid #ddd;'>Producto</th>");
        html.append("<th style='padding:6px;border:1px solid #ddd;'>Cantidad</th>");
        html.append("<th style='padding:6px;border:1px solid #ddd;'>Precio unitario</th>");
        html.append("<th style='padding:6px;border:1px solid #ddd;'>Subtotal</th></tr>");

        double total = 0.0;
        for (Map.Entry<Object, Object> e : carrito.getProductos().entrySet()) {
            Producto p = (Producto) e.getKey();
            int qty = (int) e.getValue();
            double subtotal = p.getPrecio() * qty;
            total += subtotal;

            html.append("<tr>");
            html.append("<td style='padding:6px;border:1px solid #ddd;'>").append(escapeHtml(p.getNombre())).append("</td>");
            html.append("<td style='padding:6px;border:1px solid #ddd;text-align:center;'>").append(qty).append("</td>");
            html.append("<td style='padding:6px;border:1px solid #ddd;text-align:right;'>$").append(nf.format(p.getPrecio())).append("</td>");
            html.append("<td style='padding:6px;border:1px solid #ddd;text-align:right;'>$").append(nf.format(subtotal)).append("</td>");
            html.append("</tr>");
        }

        html.append("<tr><td colspan='3' style='padding:6px;border:1px solid #ddd;text-align:right;'><strong>Total</strong></td>");
        html.append("<td style='padding:6px;border:1px solid #ddd;text-align:right;'><strong>$").append(nf.format(total)).append("</strong></td></tr>");
        html.append("</table>");
        html.append("<p>Gracias por comprar con nosotros.</p>");
        html.append("</body></html>");

        message.setContent(html.toString(), "text/html; charset=UTF-8");

        Transport.send(message);
    }

    // pequeña función para evitar inyección básica en HTML
    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#x27;");
    }
}

