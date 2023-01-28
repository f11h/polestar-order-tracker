package io.f11h.polestarordertracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.f11h.polestarordertracker.config.OrderTrackerConfigProperties;
import io.f11h.polestarordertracker.persistence.entity.Order;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private static final String EMAIL_TEMPLATE_HTML = """
            <html>
            	<head>
            		<style type="text/css">
            			body {
            				padding-top: 20px;
            				padding-left: 50px;
            				padding-right: 50px;
            				font-family: 'Arial';
            			}
            			
            			.body-text {
            				margin-left: 20px;
            				font-size: 14pt;
            			}
                        
            			.json {
            				width: 1000px;
            				overflow-x: scroll;
            				margin-left: 40px;
            				margin-top: 20px;
            				padding: 10px;
            				font-family: 'Courier New';
            				border: none;
            				border-radius: 10px;
            				background-color: #eeeeee;
            				font-size: 12pt;
            			}
            		</style>
            	</head>
            	<body>
            		<h1>
            			Your Polestar Order has been updated!
            		</h1>
            		<div class="body-text">
            			Good news - Your Polestar Order Details have just been updated from version <b><<oldVersion>></b> to <b><<newVersion>></b>.<br />
            			Check the details below to find out what has been changed.
            		</div>
            		<div class="json">
            			<<json>>
            		</div>
            	</body>
            </html>
            """;

    private static final String EMAIL_TEMPLATE_TEXT = """
            Your Polestar Order has been updated!
                      
            Good news - Your Polestar Order Details have just been updated from version <<oldVersion>> to <<newVersion>>.
            Check the details below to find out what has been changed.
              		
            <<json>>
            """;
    private final ObjectMapper objectMapper;

    private final JavaMailSender javaMailSender;

    private final OrderTrackerConfigProperties properties;

    public void notifyUser(Order order, JsonNode changes, int oldVersion, int newVersion) {
        try {
            MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, true);
            helper.setFrom(properties.getMailConfig().getFrom());
            helper.setTo(order.getNotifyEmails().split(","));
            helper.setSubject("Polestar OrderModel Update");
            helper.setText(renderEmailTemplate(EMAIL_TEMPLATE_TEXT, oldVersion, newVersion, changes, false),
                    renderEmailTemplate(EMAIL_TEMPLATE_HTML, oldVersion, newVersion, changes, true));

            javaMailSender.send(mimeMailMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String renderEmailTemplate(String template, int oldVersion, int newVersion, JsonNode json, boolean html) {
        String jsonString;
        try {
            jsonString = objectMapper.writer(new DefaultPrettyPrinter()).writeValueAsString(json);
        } catch (JsonProcessingException e) {
            log.error("Unable to convert JSON Object to String", e);
            jsonString = "Sorry - I'm not able to present you the diff-data. :-(";
        }

        if (html) {
            jsonString = jsonString.replaceAll(" ", "&nbsp;");
            jsonString = jsonString.replaceAll("\n", "<br />");
        }

        String emailText = template;
        emailText = emailText.replaceAll("<<oldVersion>>", String.valueOf(oldVersion));
        emailText = emailText.replaceAll("<<newVersion>>", String.valueOf(newVersion));
        emailText = emailText.replaceAll("<<json>>", jsonString);

        return emailText;
    }

}
