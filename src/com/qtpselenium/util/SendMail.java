package com.qtpselenium.util;
//
// 
//
////set CLASSPATH=%CLASSPATH%;activation.jar;mail.jar
//
//import javax.activation.DataHandler;
//import javax.activation.DataSource;
//import javax.activation.FileDataSource;
//import javax.mail.*;
//
//import javax.mail.internet.*;
//
//import java.util.*;
//
// 
//
//public class SendMail
//
//{
//    public static void main(String[] args) throws Exception
//
//    {
//    	Zip.zipDir(System.getProperty("user.dir")+"\\XSLT_Reports",System.getProperty("user.dir")+"\\email_xlst_reports.rar" );
//    	
//                String[] to={"vibhor.jm.com"};
//
//                String[] cc={};
//                String[] bcc={};
//
//                //This is for google
//
//                SendMail.sendMail("vibhor.jm.com",
//                		            "plasma123",
//                		            "smtp.gmail.com",
//                		            "465",
//                		            "true",
//                		            "true",
//                		            true,
//                		            "javax.net.ssl.SSLSocketFactory",
//                		            "false",
//                		            to,
//                		            cc,
//                		            bcc,
//                		            "Automation test Reports",
//                		            "Please find the reports attached.\n\n Regards\nWebMaster",
//                		        	System.getProperty("user.dir")+"\\email_xlst_reports.rar",
//                		        	"email_xlst_reports.rar");
//           
//
//    }
//
// 
//
//        public  static boolean sendMail(String userName,
//        		String passWord,
//        		String host,
//        		String port,
//        		String starttls,
//        		String auth,
//        		boolean debug,
//        		String socketFactoryClass,
//        		String fallback,
//        		String[] to,
//        		String[] cc,
//        		String[] bcc,
//        		String subject,
//        		String text,
//        		String attachmentPath,
//        		String attachmentName){
//
//
//                Properties props = new Properties();
//
//                //Properties props=System.getProperties();
//
//        props.put("mail.smtp.user", userName);
//
//        props.put("mail.smtp.host", host);
//
//                if(!"".equals(port))
//
//        props.put("mail.smtp.port", port);
//
//                if(!"".equals(starttls))
//
//        props.put("mail.smtp.starttls.enable",starttls);
//
//        props.put("mail.smtp.auth", auth);
//       // props.put("mail.smtps.auth", "true");
//
//
//                if(debug){
//
//                props.put("mail.smtp.debug", "true");
//
//                }else{
//
//                props.put("mail.smtp.debug", "false");         
//
//                }
//
//                if(!"".equals(port))
//
//        props.put("mail.smtp.socketFactory.port", port);
//
//                if(!"".equals(socketFactoryClass))
//
//        props.put("mail.smtp.socketFactory.class",socketFactoryClass);
//
//                if(!"".equals(fallback))
//
//        props.put("mail.smtp.socketFactory.fallback", fallback);
//
// 
//
//        try
//
//        {
//
//                        Session session = Session.getDefaultInstance(props, null);
//
//            session.setDebug(debug);
//
//            MimeMessage msg = new MimeMessage(session);
//
//            msg.setText(text);
//
//            msg.setSubject(subject);
//            //attachment start
//            // create the message part 
//           
//            Multipart multipart = new MimeMultipart();
//            MimeBodyPart messageBodyPart = new MimeBodyPart();
//            DataSource source = 
//              new FileDataSource(attachmentPath);
//            messageBodyPart.setDataHandler(
//              new DataHandler(source));
//            messageBodyPart.setFileName(attachmentName);
//            multipart.addBodyPart(messageBodyPart);
//            
//            // attachment ends
//
//            // Put parts in message
//            msg.setContent(multipart);
//            msg.setFrom(new InternetAddress("itsthakur@gmail.com"));
//
//                        for(int i=0;i<to.length;i++){
//
//            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
//
//                        }
//
//                        for(int i=0;i<cc.length;i++){
//
//            msg.addRecipient(Message.RecipientType.CC, new InternetAddress(cc[i]));
//
//                        }
//
//                        for(int i=0;i<bcc.length;i++){
//
//            msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc[i]));
//
//                        }
//
//            msg.saveChanges();
//
//                        Transport transport = session.getTransport("smtp");
//
//                        transport.connect(host, userName, passWord);
//
//                        transport.sendMessage(msg, msg.getAllRecipients());
//
//                        transport.close();
//
//                        return true;
//
//        }
//
//        catch (Exception mex)
//
//        {
//
//            mex.printStackTrace();
//
//                        return false;
//
//        }
//
//        }
//
// 
//
//}
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;






public class SendMail
{

	public static void sendEmail()
	{
		final String username = "vibhor@gmail.com";
		final String password = "plasma123";
		String from = "vibhor@gmail.com";
		String to = "decentvibhor@gmail.com";
		String subject = "TestSubject" + System.currentTimeMillis();
		String body = "Body" + System.currentTimeMillis();
		String file = System.getProperty("user.dir")+"\\email_xlst_reports.rar";
//    	"email_xlst_reports.rar")
		String fileName = "email_xlst_reports.rar";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(body);

			/*
			 * Code to sent attachment
			 */
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			DataSource source = new FileDataSource(file);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(fileName);
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		sendEmail();
	}
}