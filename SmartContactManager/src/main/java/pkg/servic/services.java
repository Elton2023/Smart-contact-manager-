package pkg.servic;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

@Service
public class services  {

	public boolean sendemail(String subject,String to,String msg) {
		boolean f=false;
        String from ="senders email here";//<--------------------------------ENter your email here

		
		//get the system properties
	 	Properties p= System.getProperties();
	 //	System.out.println("properties"+p);
		
//		//adding important information in properties to send mail
	 	//variable for gmail/selecting servic to use
		 String host ="smtp.gmail.com";	
		p.put("mail.smtp.host",host);
  		p.put("mail.smtp.port","465");

		p.put("mail.smtp.ssl.enable","true");
		p.put("mail.smtp.auth","true");

/**
while doing this dont forget to turn off you antivirs *
and also turn off smart secuty on google accout
*/
		
		Session s=	Session.getInstance(p,new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return  new PasswordAuthentication("your email","your gamil password"); //<------------------------------set your credentials here
			}
			
		} );
		
s.setDebug(true);//this means autocorrect if there is any mistake
//below is body for email
MimeMessage mm =new MimeMessage(s);
try {
	//adding from
	mm.setFrom(from);
	//adding recipient 
	mm.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	mm.setSubject(subject);
	
//	mm.setText(message); here we wont use this because insted of this we will add attachment  
	String AttachmentFilePath = "C:\\Users\\elton\\OneDrive\\Pictures\\Saved Pictures\\dynisha.png";
	
	MimeMultipart mmp=new MimeMultipart();//this inbuild method adds attachment
	
	MimeBodyPart textmsg =new MimeBodyPart();
	MimeBodyPart thefile =new MimeBodyPart();
	
	try {
		//attaching the text
		textmsg.setText(msg);
		
		//attaching the Attachment
		File file =new File(AttachmentFilePath);
		thefile.attachFile(file);
	
		
		//adding the attachment /mimemultipartbody to main mimemultipart
		mmp.addBodyPart(textmsg);
		mmp.addBodyPart(thefile);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	mm.setContent(mmp);
	//sending message

	Transport.send(mm);
	f=true;
} catch (MessagingException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
return f;
}
}
