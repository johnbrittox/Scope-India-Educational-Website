package com.Project.Scope.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Project.Scope.model.Registration;
import com.Project.Scope.repository.RegistraionRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;
@Service
public class RegistrationService {
	@Autowired
	private RegistraionRepository registrationrepository;
//	public void insertData(Registration reg) {
//		registrationrepository.save(reg);
//	}

	@Autowired
	private JavaMailSender javamailsender;
	private String folder="src/main/resources/static/Images";

//	public void insertData(Registration reg, String siteUrl, MultipartFile file) throws MessagingException, IOException {
//	    Path uploadPath = Paths.get(folder);
//	    if (!Files.exists(uploadPath)) {
//	        Files.createDirectories(uploadPath);
//	    }
//
//	    // Save the file to the filesystem
//	    String filename = file.getOriginalFilename();
//	    Path path = uploadPath.resolve(filename);
//	    Files.write(path, file.getBytes());
//
//	    // Store file path in the database
//	    reg.setFileupload(path.toString());
//
//	    String randomCode = RandomString.make(64);
//	    reg.setVerificationcode(randomCode);
//	    reg.setEnabled(false);
//
//	    // Save the registration and send verification email
//	    registrationrepository.save(reg);
//	    sendVerificationEmail(reg, siteUrl);
//	}
	public void insertData(Registration mail,String siteUrl,MultipartFile file) throws MessagingException, IOException{
	    Path uploadPath = Paths.get(folder);
	    if (!Files.exists(uploadPath)) {
	        Files.createDirectories(uploadPath);
	    }

	    // Save the file
	    String filename = file.getOriginalFilename();
	    Path path = uploadPath.resolve(filename);
	    Files.write(path, file.getBytes());
	    mail.setFileupload(filename); // Save the full path
	    String randomCode = RandomString.make(64); 
	    mail.setVerified(false);
	    mail.setVerificationcode(randomCode); 
	    mail.setEnabled(false); 
	  
		 registrationrepository.save(mail);
		 sendVerificationEmail(mail,siteUrl);
	}

	/*
	 * private String folder="/Scope/src/main/resources/static/Images";
	 * 
	 * public void insertData(Registration mail,String siteUrl) throws
	 * MessagingException, IOException{
	 * 
	 * 
	 * String randomCode=RandomString.make(64);
	 * mail.setVerificationcode(randomCode); mail.setEnabled(false);
	 * registrationrepository.save(mail); sendVerificationEmail(mail,siteUrl); }
	 */
	private void sendVerificationEmail(Registration reg, String siteUrl)throws MessagingException, UnsupportedEncodingException {
		String toaddr=reg.getEmail();
		String fromaddr="johnbrito938@gmail.com";
		String senderName="John Britto X ";
		String subject="Verify Registration";
		String message="Dear [[name]] please click below link to verify<h3><a href=\"[[URL]]\" target=\"_blank\">VERIFY</a></h3>";
		MimeMessage msg=javamailsender.createMimeMessage();
		MimeMessageHelper messageHelper=new MimeMessageHelper(msg);
//		 siteUrl = "http://localhost:8082";  // or use your server's domain

		messageHelper.setFrom(fromaddr,senderName);
		messageHelper.setTo(toaddr);
		messageHelper.setSubject(subject);
		message=message.replace("[[name]]",reg.getFirstName()+" "+reg.getLastName());
		String url=siteUrl+"/verify?code="+reg.getVerificationcode();
		
		message=message.replace("[[URL]]",url);
		messageHelper.setText(message,true);
		javamailsender.send(msg);
	}
	public boolean verify(String verificationcode) {
		Registration mail=registrationrepository.findByVerificationcode(verificationcode);
		if(mail==null||mail.isEnabled()) {
		return false;
		}else {
			mail.setVerificationcode(null);
			mail.setEnabled(true);
			registrationrepository.save(mail);
			return true;
		}
		
	}
	public void sendEmail(String email, String OTP)throws MessagingException, UnsupportedEncodingException {
		System.out.println("otp is "+OTP);

		String fromaddr="johnbrito938@gmail.com";
		String senderName="John Britto X";
		String subject="OTP Sending";
		String message="Please check your otp: [[OTP]]";
		MimeMessage msg=javamailsender.createMimeMessage();
		MimeMessageHelper messageHelper=new MimeMessageHelper(msg);
		
		messageHelper.setFrom(fromaddr,senderName);
		messageHelper.setTo(email);
		
		message=message.replace("[[OTP]]",OTP);
		messageHelper.setText(message,true);
		javamailsender.send(msg);
	}
	public Registration getByEmail(String email) {
		// TODO Auto-generated method stub
		return registrationrepository.findByEmail(email);
	}

	
	
		
	}



