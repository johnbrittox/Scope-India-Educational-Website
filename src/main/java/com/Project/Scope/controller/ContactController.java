package com.Project.Scope.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.Project.Scope.model.Contact;
import com.Project.Scope.service.ContactService;

import jakarta.validation.Valid;


@Controller
public class ContactController {
	 @Autowired
	    private JavaMailSender mailSender;
	 @Autowired
	    private ContactService contactservice;
	
	@RequestMapping("/contact")
	public String contact(Model model) {
		model.addAttribute("contact",new Contact());
		return "contact";

}
	@PostMapping("/sendcontact")
    public String sendContact(@Valid @ModelAttribute("contact") Contact contact, 
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "contact"; // Return form if validation fails
        }
        
        try {
            // Compose email
            SimpleMailMessage message = new SimpleMailMessage();
            
            message.setTo("johnbrito938@gmail.com");  //Set recipient email
            message.setSubject(contact.getSubject());
            message.setText("Name: " + contact.getName() + "\nEmail: " + contact.getEmail() +
                            "\nMessage: " + contact.getMessage());

            // Send email
            mailSender.send(message);
            
            // Save contact details to the database
            contactservice.saveContact(contact);


            // Success message
            model.addAttribute("message", "Email sent successfully! We will contact you soon.");
        } catch (MailException e) {
            // Failure message
            model.addAttribute("error", "Unable to send email right now. Please try again later.");
        }
        
        return "contact"; // Return to contact page with message
    }
	}

