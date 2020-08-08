package net.chen.utils;

import lombok.extern.slf4j.Slf4j;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * Created by Chen
 * 2020/8/4 21:44
 */
@Slf4j
public class EmailSender {
    private static final String TAG = "EmailSender";
    private static  Session  session ;
    private static String user;

    private  MimeMessage msg;
    private String text;
    private String html;
    private List<MimeBodyPart> attachments = new ArrayList<MimeBodyPart>();
    // AVIGHTZYNSINDXZX
    private EmailSender(){
        EmailSender.config(EmailSender.SMTP_163(false),"xiaomoshengdewo@163.com","AVIGHTZYNSINDXZX");
    }


    public static Properties defaultConfig(Boolean debug){
        Properties pro = new Properties();
        pro.put("mail.smtp.auth","true");
        pro.put("mail.smtp.ssl.enable","true");
        pro.put("mail.transport.protocol","smtp");
        pro.put("mail.debug",null !=debug ? debug.toString() : "false");
        pro.put("mail.smtp.timeout","10000");
        pro.put("mail.smtp.port","465");
        return pro;
    }
    public static Properties SMTP_ENT_QQ(boolean debug) {
        Properties pro = defaultConfig(debug);
        pro.put("mail.smtp.host","smtp.exmail.qq.com");
        return pro;
    }
    public static Properties SMTP_QQ(boolean debug){
        Properties pro = defaultConfig(debug);
        pro.put("mail.smtp.host","stmp.qq.com");
        return pro;
    }
    public static Properties SMTP_163(boolean debug){
        Properties pro = defaultConfig(debug);
        pro.put("mail.smtp.host","stmp.163.com");
        return pro;
    }

    public static void config(Properties pro,final String username,final String password){
        pro.setProperty("username",username);
        pro.setProperty("password",password);
        config(pro);
    }
    public static void config(Properties pro ){
        final String username = pro.getProperty("username");
        final String password = pro.getProperty("password");
        user = username;
        session = Session.getInstance(pro, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });
    }

    public static EmailSender subject(String subject){
        EmailSender EmailSender = new EmailSender();
        EmailSender.msg = new MimeMessage(session);
        try {
            EmailSender.msg.setSubject(subject,"utf-8");
        }catch (Exception e){
            log.info(TAG,e+"");
        }
        return EmailSender;
    }



    public EmailSender from(String nickName){
        return from(nickName,user);
    }



    public EmailSender from(String nickName,String form){
        try {
            String encodeNickName = MimeUtility.encodeText(nickName);
            msg.setFrom(new InternetAddress(encodeNickName+ " <" + form +">"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    public EmailSender replyTo(String... replyTo){
            String result = Arrays.asList(replyTo).toString().replaceAll("(^\\[|\\]$)","").replace(", ",",");
            try {
                msg.setReplyTo(InternetAddress.parse(result));
            }catch (Exception e){
                e.printStackTrace();
            }
            return this;
    }

    public EmailSender to(String... to) throws MessagingException {
        return addRecipients(to, Message.RecipientType.TO);
    }
    public EmailSender to(String to) throws MessagingException {
        return addRecipients(to, Message.RecipientType.TO);
    }

    public EmailSender cc(String... cc) throws MessagingException {
        return addRecipients(cc, Message.RecipientType.CC);
    }

    public EmailSender cc(String cc) throws MessagingException {
        return addRecipients(cc, Message.RecipientType.CC);
    }

    public EmailSender bcc(String... bcc) throws MessagingException {
        return addRecipients(bcc, Message.RecipientType.BCC);
    }

    public EmailSender bcc(String bcc) throws MessagingException {
        return addRecipients(bcc, Message.RecipientType.BCC);
    }


    public EmailSender addRecipients(String[] recipients, Message.RecipientType type) throws MessagingException {
        String result = Arrays.asList(recipients).toString().replace("(^\\[|\\]$)","").replace(",","");
        msg.setRecipients(type,InternetAddress.parse(result));
        return this;
    }
    public EmailSender addRecipients(String recipients, Message.RecipientType type) throws MessagingException {
        msg.setRecipients(type,InternetAddress.parse(recipients.replace(";",",")));
        return this;
    }



    public EmailSender text(String text){
        this.text = text;
        return this;
    }
    public EmailSender html(String html){
        this.html = html;
        return this;
    }
    public EmailSender attach(File file){
        attachments.add(createAttachment(file,null));
        return this;
    }
    public EmailSender attach(File file,String fileName){
        attachments.add(createAttachment(file,fileName));
        return this;
    }
    public EmailSender attach(URL url,String fileName){
        attachments.add(createAttachment(url,fileName));
        return this;
    }


    private MimeBodyPart createAttachment(File file,String fileName) {
        MimeBodyPart attachmentPart = new MimeBodyPart();
        FileDataSource fds = new FileDataSource(file);
        try {
            attachmentPart.setDataHandler(new DataHandler(fds));
            attachmentPart.setFileName(null == fileName ? MimeUtility.encodeText(fds.getName()):MimeUtility.encodeText(fileName));
        }catch (Exception e){
            e.printStackTrace();
        }

        return attachmentPart;
    }




    private MimeBodyPart createAttachment(URL url, String fileName) {
        MimeBodyPart attachmentPart = new MimeBodyPart();
        DataHandler dataHandler = new DataHandler(url);
        try {
            attachmentPart.setDataHandler(dataHandler);
            attachmentPart.setFileName(null == fileName ? MimeUtility.encodeText(fileName) : MimeUtility.encodeText(fileName));
        }catch (Exception e){
            e.printStackTrace();
        }
        return attachmentPart;

    }

    public void send(){
        if (text == null && html == null){
            throw new IllegalArgumentException("At least context has to be provided: Text or Html");
        }
        MimeMultipart cover;
        boolean usingAlternative = false;
        boolean hasAttachments = attachments.size() > 0;
        try {
            if (text !=null && html == null){
                cover = new MimeMultipart("mixed");
                cover.addBodyPart(textPart());
            }else if (text == null && html != null){
                cover = new MimeMultipart("mixed");
                cover.addBodyPart(htmlPart());
            }else {
                cover = new MimeMultipart("alternative");
                cover.addBodyPart(textPart());
                cover.addBodyPart(htmlPart());
                usingAlternative = true;
            }

            MimeMultipart content = cover;
            if (usingAlternative && hasAttachments){
                content = new MimeMultipart("mixed");
                content.addBodyPart(toBodyPart(cover));
            }

            for (MimeBodyPart attachment : attachments){
                content.addBodyPart(attachment);
            }
            msg.setContent(content);
            msg.setSentDate(new Date());
            Transport.send(msg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private MimeBodyPart toBodyPart(MimeMultipart cover) throws MessagingException {
        MimeBodyPart wrap = new MimeBodyPart();
        wrap.setContent(cover);
        return wrap;
    }

    private MimeBodyPart textPart() throws MessagingException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setText(text);
        return bodyPart;
    }
    private MimeBodyPart htmlPart() throws MessagingException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(html,"text/html; charset=utf-8");
        return bodyPart;
    }

    public static void sendRegisterVerifyCode(String code,String address) throws Exception{
        EmailSender.subject("Ay博客系统注册验证码")
                .from("Ay博客系统")
                .text("您的验证码是" + code + "验证码有效期10分钟,若非本人操作，请忽略")
                .to(address)
                .send();

    }
}


