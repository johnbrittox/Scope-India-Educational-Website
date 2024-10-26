package com.Project.Scope.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.Project.Scope.model.City;
import com.Project.Scope.model.Country;
import com.Project.Scope.model.PasswordForm;
import com.Project.Scope.model.Registration;
import com.Project.Scope.model.State;
import com.Project.Scope.repository.CityRepository;
import com.Project.Scope.repository.RegistraionRepository;
import com.Project.Scope.service.CityService;
import com.Project.Scope.service.CountryService;
import com.Project.Scope.service.CourseService;
import com.Project.Scope.service.RegistrationService;
import com.Project.Scope.service.StateService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
@Controller
public class RegistrationController {
@Autowired
private RegistrationService registrationservice;
@Autowired
private RegistraionRepository registrationrepository;
@Autowired
private CountryService countryService;
@Autowired
private StateService stateservice;

@Autowired
private CityService cityservice;
@Autowired 
private CityRepository cityRepository;
@Autowired
private CourseService courseservice;
public List<City> getCityBy(State stateId){
	return cityRepository.findByState(stateId);
	
}

/*
 * @RequestMapping("") public String home(Model model) {
 * model.addAttribute("register",new Registration()); return "registration"; }
 */
@RequestMapping("/index")
public String index() {
	return "index";
}
@RequestMapping("/about")
public String about() {
	return "about";
}
@RequestMapping("/ab")
public String ab() {
	return "ab";
}

@RequestMapping("/login")
public String showloginpage(Model model) {
	model.addAttribute("login", new Registration());
	return "login";
}
@RequestMapping("/registration")
public String form(Model model) {
	model.addAttribute("countries", countryService.countrylist());
	model.addAttribute("register",new Registration());
	return "registration";
	
}

@RequestMapping("send")
public String send(@Valid @ModelAttribute("register") Registration reg, 
                   @RequestParam("avatar") MultipartFile file, 
                   BindingResult result, 
                   Model model, 
                   HttpServletRequest request) throws MessagingException, IOException {
    
    // File validation
    if (file.isEmpty()) {
        result.rejectValue("fileupload", "error.fileupload", "Please select an image to upload");
    } else if (!file.getContentType().startsWith("image/")) {
        result.rejectValue("fileupload", "error.fileupload", "Only image files are allowed");
    }

    // Check for validation errors
    if (result.hasErrors()) {
        return "registration";
    }

    // Save file and set the file path in the model
    registrationservice.insertData(reg, getSiteURL(request), file);
    
    // Add registration object to the model
    model.addAttribute("register", reg);
    
    return "checkmail";
}






/*
 * @RequestMapping("send") public String
 * register(@Valid @ModelAttribute("register")Registration reg,BindingResult
 * result,Model model,HttpServletRequest request) throws MessagingException,
 * IOException { if(result.hasErrors()) { return "redirect:/registration"; }
 * else { registrationservice.insertData(reg, getSiteURL(request));
 * model.addAttribute("register",reg); return "about"; } }
 */
private String getSiteURL(HttpServletRequest request) {
	// TODO Auto-generated method stub
	String siteurl=request.getRequestURL().toString();
	return siteurl.replace(request.getServletPath(),"");
}
@RequestMapping("verify")
public String verify(@Param("code")String code) {
	if(registrationservice.verify(code)) {
		return "success";
	}
	else {
		return "error";
	}
}
@GetMapping("/state/{countryid}")
public @ResponseBody Iterable<State>getStateByCountry(@PathVariable Country countryid){
	return stateservice.getStateBy(countryid);
}

@GetMapping("/city/{stateid}")
public @ResponseBody List<City> getCityByState(@PathVariable State stateid){
	return cityservice.getCityBy(stateid);
}

//otp

@GetMapping("/generate-temp-password")
public String login(Model model) {
model.addAttribute("register",new Registration());
return "send-otp";
}
@PostMapping("/generate-temp-password")
public String sendOtp(Model model,@RequestParam("email")String email,Registration register) throws UnsupportedEncodingException, MessagingException {
	Registration existingregister=registrationrepository.findByEmail(email);
	System.out.println("check1");
	if(existingregister!=null) {
		System.out.println("check2");

		String newOtp=generateRandomOtp();
		existingregister.setOTP(newOtp);
		System.out.println("check3");

		registrationrepository.save(existingregister);
		System.out.println("check4");

		registrationservice.sendEmail(email,newOtp);
		System.out.println("check5");

		model.addAttribute("email",email);
		System.out.println("check6");

		return "verify-otp";
	}else {
		model.addAttribute("error","Email not found.please register first.");
		return "registration";
	}
	}


@PostMapping("/verify-otp")
public String verifyOtp(@RequestParam("email")String email,@RequestParam("OTP")String enteredOTP,Model model) {
	Registration register=registrationrepository.findByEmail(email);
	if(register!=null&&register.getOTP()!=null&&register.getOTP().equals(enteredOTP)) {
		register.setVerified(true);
		registrationrepository.save(register);
		model.addAttribute("email",email);
		return "set-new-password";
	}else {
		model.addAttribute("email","Invalid OTP.Please try again.");
		return "verify-otp";
	}
	
	
	
}
private String generateRandomOtp() {
	String OTP=String.valueOf(new Random().nextInt(900000)+100000);
	return OTP;
}
@PostMapping("/set-new-password")
public String setNewPassword(@RequestParam("email") String email, 
                             @RequestParam("password") String newPassword, 
                             Model model) {

   
    Registration register = registrationrepository.findByEmail(email);
    
    if (register != null) {
       
        register.setPassword(newPassword); 
        registrationrepository.save(register);
        
        model.addAttribute("message", "Password has been successfully updated.");
        return "login"; // Redirect to the login page or success page
    } else {
        model.addAttribute("error", "Error updating password. User not found.");
        return "set-new-password";
    }
}
//
//@PostMapping("/login")
//public String log(@RequestParam("email")String email,@RequestParam("password")String password,Model model) {
//	Registration user =registrationrepository.findByEmailAndPassword(email, password);
//	if (user != null) {
//	return "dashboard";
//}else {
//	model.addAttribute("email","Invalid OTP.Please try again.");
//	return "error";
//}
//}

//cookies first
//@PostMapping("/login")
//public String loginCheck(@RequestParam("email") String email, @RequestParam("password") String password,
//                       @RequestParam(value = "cookies", required = false) String loggedIn,
//                       HttpServletResponse response, HttpServletRequest request,HttpSession session, Model model) {
//
//  Registration user = registrationrepository.findByEmailAndPassword(email, password);
//
//  if (user != null) {
//	  
//	  request.getSession().setAttribute("emailid", email);
//
//      if (loggedIn != null) {
//          // "Keep me logged in" selected, set cookie and don't set session
//          Cookie ck = new Cookie("username", email);
//          ck.setMaxAge(100); // Set cookie for 1 minute
//          ck.setPath("/");
//          response.addCookie(ck);
//      } 
//      
//      // Redirect to /dashboard after successful login
//      return "redirect:/dashboard";
//  } else {
//      // Invalid login, send back to login page with error
//      model.addAttribute("error", "Invalid email or password");
//      return "login";
//  }
//}
//
//cookie second
@PostMapping("/login")
public String loginCheck(@RequestParam("email") String email, 
                         @RequestParam("password") String password,
                         @RequestParam(value = "cookies", required = false) String loggedIn,
                         HttpServletResponse response, 
                         HttpServletRequest request,
                         HttpSession session, 
                         Model model) {

    // Debugging - Print email and password to verify they are received
    System.out.println("Email: " + email + ", Password: " + password);

    // Check if the user exists with the provided email and password
    Registration user = registrationrepository.findByEmailAndPassword(email, password);

    if (user != null) {
        // Debugging - Print to verify user login success
        System.out.println("Login successful for user: " + email);

        // Set session attribute for email
        session.setAttribute("emailid", email);

        // If "Keep me logged in" is selected, set a cookie
        if (loggedIn != null) {
            System.out.println("'Keep me logged in' selected. Setting cookie for user: " + email);
            Cookie ck = new Cookie("username", email);
            ck.setMaxAge(60 * 60 * 24 * 30); // Set cookie for 30 days
            ck.setPath("/");
            response.addCookie(ck);
        }

        // Redirect to the dashboard after successful login
        return "redirect:/dashboard";
    } else {
        // Debugging - Print if login fails
        System.out.println("Invalid email or password for user: " + email);
        
        // Invalid login, send back to login page with error
        model.addAttribute("error", "Invalid email or password");
        return "login";
    }
}

//1st
//@GetMapping("/dashboard")
//public String dashboard( 
//
//		HttpServletRequest request, HttpServletResponse response, Model model,HttpSession session) {
//  // Disable cache to prevent accessing dashboard after logout or session expiry
//	String mail=(String) session.getAttribute("emailid");
//	System.out.println(mail);
//	 Registration user = registrationservice.getByEmail(mail);
//		System.out.println(user);
//	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//  response.setHeader("Pragma", "no-cache");
//  response.setDateHeader("Expires", 0);
//
//  // Check if the cookie exists and is valid
//  Cookie[] cookies = request.getCookies();
//  if (cookies != null) {
//      for (Cookie cookie : cookies) {
//          if ("username".equals(cookie.getName())) {
//              // User has valid cookie, allow access to dashboard
//        	  if (user != null) {
//        		  model.addAttribute("availableCourses",courseservice.courselist());	
//        		  model.addAttribute("username",mail);
//        		  System.out.println(user.getEmail());
//        		  model.addAttribute("student",user);
//        		  
//        		
//          }
//        	  model.addAttribute("student",user);
//              return "dashboard";
//          }
//      }
//  }
//
//  
//  // If neither session nor cookie is valid, show session expired error
//  model.addAttribute("error", "Session expired, please log in again");
//  return "login";
//}

//keepmeloggedin or not
@GetMapping("/dashboard")
public String dashboard(HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) {
    // Disable cache to prevent accessing the dashboard after logout or session expiry
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    // Get the email from the session
    String mail = (String) session.getAttribute("emailid");
    System.out.println("Session email: " + mail);

    // If the session is valid, allow access to the dashboard
    if (mail != null) {
        Registration user = registrationservice.getByEmail(mail);
        System.out.println("User retrieved: " + user);

        // Check if the user is found in the database
        if (user != null) {
            model.addAttribute("availableCourses", courseservice.courselist());
            model.addAttribute("username", mail);
            model.addAttribute("student", user);
            return "dashboard";
        }
    }

    // Check the cookies for "Keep me logged in"
    Cookie[] cookies = request.getCookies();
    boolean cookieExists = false;
    
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("username".equals(cookie.getName())) {
                cookieExists = true; // Cookie found
                // Retrieve the email from the cookie
                String cookieEmail = cookie.getValue();
                Registration user = registrationservice.getByEmail(cookieEmail);
                System.out.println("Cookie email: " + cookieEmail);

                // Check if the user is found in the database
                if (user != null) {
                    // Set session for the user again
                    session.setAttribute("emailid", cookieEmail);

                    // Allow access to the dashboard
                    model.addAttribute("availableCourses", courseservice.courselist());
                    model.addAttribute("username", cookieEmail);
                    model.addAttribute("student", user);
                    return "dashboard";
                }
            }
        }
    }

    // If neither session nor cookie is valid, show session expired error
    // If cookies were checked and not found, invalidate session
    if (!cookieExists) {
        session.invalidate(); // Invalidate the session if cookie is missing
        model.addAttribute("error", "Session expired, please log in again");
    }

    return "login";
}

//changepassword
//
//@GetMapping("/change-password")
//public String showChangePasswordForm(Model model) {
//    model.addAttribute("passwordForm", new PasswordForm(null, null));
//    return "change-password";
//}
//
//@PostMapping("/change-password")
//public String changePassword(@ModelAttribute PasswordForm passwordForm, HttpSession session) {
//    // Validate and update password logic here
    // ...

    // Invalidate session to log out the user
//    session.invalidate();
//    return "redirect:/login?passwordChanged";
//}
@RequestMapping("/profileedit")
public String profileedit() {
	return "profileedit";
}



}

