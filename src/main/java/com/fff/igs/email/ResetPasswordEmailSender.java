package com.fff.igs.email;

import com.fff.igs.gcs.StorageManager;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;

public class ResetPasswordEmailSender {

    private static final Logger LOGGER = Logger.getLogger(ResetPasswordEmailSender.class.getName());

    public String sendTempPasswordMail(String strTargetEmail) {
        String strNewPassword = genNewPassword();

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(System.getProperty("email_sender_address"), System.getProperty("email_sender_title")));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(strTargetEmail, ""));
            msg.setSubject(System.getProperty("email_reset_password_title"));

            String headerImageCid = System.getProperty("cs_system_resource_email_title_image_name");
            InputStream pngInputStream = getTitleImageFromCS();
            DataSource ds = new ByteArrayDataSource(pngInputStream, "image/png");
            MimeBodyPart imagePart = new MimeBodyPart();
            imagePart.setDataHandler(new DataHandler(ds));
            imagePart.setFileName(headerImageCid);
            imagePart.setHeader("Content-Type", "image/png");
            imagePart.addHeader("Content-ID", "<" + headerImageCid + ">");

            StringBuilder strHtmlBody = new StringBuilder();
            strHtmlBody.append("<h2 id=\"mcetoc_1cg8g9tj04\">&nbsp;</h2>\n<p><img src=\"cid:");
            strHtmlBody.append(System.getProperty("cs_system_resource_email_title_image_name"))
                    .append("\"/></p>").append("<h2 id=\"mcetoc_1cg8g9v7n5\" style=\"text-align: center;\"></h2>\n")
                    .append("<h1 id=\"mcetoc_1cg8g9rit3\" style=\"text-align: center;\"><span style=\"color: #ffcc00;\">" +
                            "<em><strong>你  讓世界更美好</strong></em></span></h1>\n")
                    .append("<p>&nbsp;</p>\n").append("<p>Hi 親愛的朋友，系統已為您產生一組臨時密碼。</p>");

            strHtmlBody.append("<p>您的臨時密碼為 : <strong>");
            strHtmlBody.append(strNewPassword);
            strHtmlBody.append("<strong>&nbsp;&nbsp;<br /><br /></p>");
            strHtmlBody.append("<p>臨時密碼為數字0到9 以及 大寫字母組成，請以此組密碼登入，並完成密碼修改。</p>\n" +
                    "<p>臨時密碼為四位數字，臨時密碼當天有效，如果有重覆產生，請以最後收到的臨時密碼為主。</p>\n" +
                    "<p>&nbsp;</p>\n" +
                    "<p>&nbsp;</p>\n" +
                    "<p>&nbsp;</p>\n" +
                    "<p>&nbsp;</p>\n" +
                    "<p>&nbsp;</p>\n" +
                    "<h5 style=\"text-align: center;\">Copyright &copy; 545 Corp.</h5>\n" +
                    "<h5 style=\"text-align: center;\">版權所有&copy;&nbsp;五四五資訊股份有限公司</h5>\n" +
                    "<h5 style=\"text-align: center;\">台北市大安區忠孝東路四段166號10樓</h5>");

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent("<html><body>" + strHtmlBody.toString() + "</body></html>", "text/html");

            final Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(imagePart);
            multipart.addBodyPart(htmlPart);

            msg.setContent(multipart);
            msg.saveChanges();

            Transport.send(msg);
        } catch (MessagingException | IOException e) {
            LOGGER.warning(e.getMessage());
        }
        return strNewPassword;
    }

    private String genNewPassword() {
        StringBuilder strNewPassword = new StringBuilder();

        final int CODE_SIZE = 8;

        String SALT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        Random rnd = new Random();
        while (strNewPassword.length() < CODE_SIZE) {
            int index = (int) (rnd.nextFloat() * SALT_CHARS.length());
            strNewPassword.append(SALT_CHARS.charAt(index));
        }

        return strNewPassword.toString();
    }

    private InputStream getTitleImageFromCS() throws IOException {
        StorageManager sm = new StorageManager();
        String strImageFolder = System.getProperty("cs_system_resource_email");
        String strImageName = System.getProperty("cs_system_resource_email_title_image_name");
        String strFullPath = strImageFolder + "/" + strImageName;
        return sm.getEmailTitleImage(strFullPath);
    }
}
