package com.Project.Scope.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Project.Scope.model.City;
import com.Project.Scope.model.Country;
import com.Project.Scope.model.Course;
import com.Project.Scope.model.Registration;
import com.Project.Scope.model.State;
import com.Project.Scope.repository.CityRepository;
import com.Project.Scope.repository.CountryRepository;
import com.Project.Scope.repository.CourseRepository;
import com.Project.Scope.repository.RegistraionRepository;
import com.Project.Scope.repository.StateRepository;
import com.Project.Scope.service.CourseService;
import com.Project.Scope.service.RegistrationService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class CourseController {
	@Autowired
	private RegistraionRepository registrationRepository;
	@Autowired
	private RegistrationService registrationservice;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private CourseService courseservice;
	@RequestMapping("/course")
    public String courses(Model model) {
		model.addAttribute("courses", courseservice.courselist());
        model.addAttribute("course", new Registration());
        return "course";
    }
	@GetMapping("coursedetails/{id}")
	public String coursedetail(@PathVariable int id,Model model) {
		model.addAttribute("course",courseservice.getCourseByCourseId(id));
		return "courses";		
	}
//	@GetMapping("/courses")
//	public String java(HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) {
//	    // Disable cache to prevent accessing the page after logout or session expiry
//	    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//	    response.setHeader("Pragma", "no-cache");
//	    response.setDateHeader("Expires", 0);
//
//	    // Get the email from the session
//	    String mail = (String) session.getAttribute("emailid");
//	    System.out.println("Session email: " + mail);
//
//	    // If the session is valid, allow access to the course details
//	    if (mail != null) {
//	        Registration user = registrationservice.getByEmail(mail);
//	        System.out.println("User retrieved: " + user);
//
//	        // Check if the user is found in the database
//	        if (user != null) {
//	            // Add available courses and user data to the model
//	            model.addAttribute("availableCourses", courseservice.courselist());
//	            model.addAttribute("username", mail);
//	            model.addAttribute("student", user);
//	            return "courses"; // Returning the Thymeleaf template for Java course page
//	        }
//	    }
//
//	    // Check cookies for "Keep me logged in" feature
//	    Cookie[] cookies = request.getCookies();
//	    boolean cookieExists = false;
//
//	    if (cookies != null) {
//	        for (Cookie cookie : cookies) {
//	            if ("username".equals(cookie.getName())) {
//	                cookieExists = true; // Cookie found
//	                // Retrieve the email from the cookie
//	                String cookieEmail = cookie.getValue();
//	                Registration user = registrationservice.getByEmail(cookieEmail);
//	                System.out.println("Cookie email: " + cookieEmail);
//
//	                // Check if the user is found in the database
//	                if (user != null) {
//	                    // Set session for the user again
//	                    session.setAttribute("emailid", cookieEmail);
//
//	                    // Add available courses and user data to the model
//	                    model.addAttribute("availableCourses", courseservice.courselist());
//	                    model.addAttribute("username", cookieEmail);
//	                    model.addAttribute("student", user);
//	                    return "courses"; // Returning the Thymeleaf template for Java course page
//	                }
//	            }
//	        }
//	    }
//
//	    // If session or cookies are invalid, invalidate session and return to login page
//	    if (!cookieExists) {
//	        session.invalidate(); // Invalidate the session if no valid cookie found
//	        model.addAttribute("error", "Session expired, please log in again");
//	    }
//
//	    return "login"; // Redirect to login page if no session or valid cookie found
//	}

	
//    @PostMapping("/signup-course")
//    public String course(HttpSession session, Model model, @RequestParam("Course") String course) {
//        String username = (String) session.getAttribute("emailid");
//        Registration regform = registrationRepository.findByEmail(username);
//        if (regform != null) {
//            model.addAttribute("course", new Registration());
//            regform.setCourse(course);
//            registrationRepository.save(regform);
//            return "redirect:/dashboard";
//        } else {
//            return "Error";
//        }
//    }
	//signup with id
//	@PostMapping("/signup-course")
//	public String courseSignup(HttpSession session, Model model, @RequestParam("courseId") String courseId) {
//	    String username = (String) session.getAttribute("emailid");
//
//	    if (username == null) {
//	        model.addAttribute("error", "Session expired. Please log in again.");
//	        return "redirect:/login";
//	    }
//
//	    Registration regform = registrationRepository.findByEmail(username);
//
//	    if (regform != null) {
//	        regform.setCourse(courseId);
//	        registrationRepository.save(regform);
//	        return "redirect:/dashboard";
//	    } else {
//	        model.addAttribute("error", "User not found. Please register first.");
//	        return "errorPage"; // Redirect to a proper error page
//	    }
//	}
//signup with name
	@PostMapping("/signup-course")
	public String courseSignup(HttpSession session, Model model, @RequestParam("courseId") Integer courseId) {
	    String username = (String) session.getAttribute("emailid");

	    if (username == null) {
	        model.addAttribute("error", "Session expired. Please log in again.");
	        return "redirect:/login";
	    }

	    Registration regform = registrationRepository.findByEmail(username);
	    Course course = courseRepository.findById(courseId).orElse(null); // Fetch the course based on courseId

	    if (regform != null && course != null) {
	        regform.setCourse(course.getCoursename()); // Save course name instead of course ID
	        registrationRepository.save(regform);
	        
	        model.addAttribute("student", regform); // Pass the student/registration form to the view
	        return "redirect:/dashboard";
	    } else {
	        model.addAttribute("error", "User not found or invalid course. Please register first.");
	        return "errorPage"; // Redirect to a proper error page
	    }
	}


//   //1st edit
//    @GetMapping("/editprofile")
//    public String showEditProfilePage(HttpSession session, Model model) {
//        String username = (String) session.getAttribute("emailid");
//       Registration regform = registrationRepository.findByEmail(username);
//
//        if (regform != null) {
//            model.addAttribute("edit", regform);
//            return "edit-profile"; // return the view for editing profile
//        } else {
//            model.addAttribute("error", "User not found.");
//            return "error";
//        }
//    }
	//2nd edit
//	@GetMapping("/editprofile")
//	public String showEditProfilePage(HttpSession session, Model model) {
//	    String username = (String) session.getAttribute("emailid");
//	    
//	    // Debug logging
//	    System.out.println("Session emailid: " + username);
//	    
//	    if (username == null || username.isEmpty()) {
//	        model.addAttribute("error", "No email found in session. Please log in.");
//	        return "login";
//	    }
//
//	    Registration regform = registrationRepository.findByEmail(username);
//
//	    if (regform != null) {
//	        model.addAttribute("student", regform);
//	        return "edit-profile";
//	    } else {
//	        model.addAttribute("error", "User not found.");
//	        return "edit-profile";
//	    }
//	}

//1st postedit
//    @PostMapping("/editprofile")
//    public String editProfile(HttpSession session, Model model,
//    		
//    		                  @RequestParam("hobbies")String hobbies,
//                              @RequestParam("firstName") String firstName,
//                              @RequestParam("lastName") String lastName,
//                              @RequestParam("phoneNumber") long phoneNumber,
//                              @RequestParam("dateOfBirth") String dateOfBirthStr) {
//
//        String username = (String) session.getAttribute("emailid");
//        Registration regform = registrationRepository.findByEmail(username);
//
//        if (regform != null) {
//            regform.setFirstName(firstName);
//            regform.setLastName(lastName);
//            regform.setPhoneNumber(phoneNumber);
//            regform.setHobbies(hobbies);
//            
//            // Parse the date
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            try {
//                Date dateOfBirth = dateFormat.parse(dateOfBirthStr);
//                regform.setDateOfBirth(dateOfBirth);
//            } catch (ParseException e) {
//                model.addAttribute("error", "Invalid date format.");
//                return "error";
//            }
//
//            registrationRepository.save(regform);
//            model.addAttribute("message", "Profile updated successfully.");
//            return "redirect:/dashboard";
//        } else {
//            model.addAttribute("error", "User not found.");
//            return "error";
//        }
//    }
	
	//2nd post edit
//    @PostMapping("/editprofile")
//    public String editProfile(HttpSession session, Model model,
//                              @RequestParam("hobbies") String hobbies,
//                              @RequestParam("firstName") String firstName,
//                              @RequestParam("lastName") String lastName,
//                              @RequestParam("phoneNumber") long phoneNumber,
//                              @RequestParam("dateOfBirth") String dateOfBirthStr,
//                              @RequestParam("fileupload") MultipartFile file) {
//
//        String username = (String) session.getAttribute("emailid");
//        Registration regform = registrationRepository.findByEmail(username);
//
//        if (regform != null) {
//            regform.setFirstName(firstName);
//            regform.setLastName(lastName);
//            regform.setPhoneNumber(phoneNumber);
//            regform.setHobbies(hobbies);
//
//            // Parse the date
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            try {
//                Date dateOfBirth = dateFormat.parse(dateOfBirthStr);
//                regform.setDateOfBirth(dateOfBirth);
//            } catch (ParseException e) {
//                model.addAttribute("error", "Invalid date format.");
//                return "error";
//            }
//
//            // Handle file upload
//            if (!file.isEmpty()) {
//                // Define the path where you want to save the file
//                String uploadDirectory = "src/main/resources/static/Images";
//                		
//                String fileName = file.getOriginalFilename();
//                Path filePath = Paths.get(uploadDirectory, fileName);
//
//                try {
//                    // Save the file to the specified directory
//                    Files.write(filePath, file.getBytes());
//
//                    // Update the fileupload field in the database
//                    regform.setFileupload(fileName);
//                } catch (IOException e) {
//                    model.addAttribute("error", "Error saving file.");
//                    return "error";
//                }
//            }
//
//            // Save the updated profile details
//            registrationRepository.save(regform);
//            model.addAttribute("message", "Profile updated successfully.");
//            return "redirect:/dashboard";
//        } else {
//            model.addAttribute("error", "User not found.");
//            return "error";
//        }
//    }
//3rd edit
	@PostMapping("/editprofile")
	public String editProfile(HttpSession session, Model model,
	                          @RequestParam("hobbies") String hobbies,
	                          @RequestParam("firstName") String firstName,
	                          @RequestParam("lastName") String lastName,
	                          @RequestParam("phoneNumber") long phoneNumber,
	                          @RequestParam("dateOfBirth") String dateOfBirthStr,
	                          @RequestParam("fileupload") MultipartFile file,
	                          @RequestParam("country") Integer countryId,
	                          @RequestParam("state") Integer stateId,
	                          @RequestParam("city") Integer cityId) {

	    String username = (String) session.getAttribute("emailid");
	    Registration regform = registrationRepository.findByEmail(username);

	    if (regform != null) {
	        regform.setFirstName(firstName);
	        regform.setLastName(lastName);
	        regform.setPhoneNumber(phoneNumber);
	        regform.setHobbies(hobbies);

	        // Parse the date
			/*
			 * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); try { Date
			 * dateOfBirth = dateFormat.parse(dateOfBirthStr);
			 * regform.setDateOfBirth(dateOfBirth); } catch (ParseException e) {
			 * model.addAttribute("error", "Invalid date format."); return "error"; }
			 */

	     // Define your date format
	     DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	     try {
	         // Parse the date string to LocalDate
	         LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr, dateFormat);
	         
	         // Set the parsed LocalDate in your registration form
	         regform.setDateOfBirth(dateOfBirth);
	     } catch (DateTimeParseException e) {
	         model.addAttribute("error", "Invalid date format.");
	         return "error";
	     }

	        // Set country, state, and city
	        Country country = countryRepository.findById(countryId).orElse(null);
	        State state = stateRepository.findById(stateId).orElse(null);
	        City city = cityRepository.findById(cityId).orElse(null);

	        if (country != null && state != null && city != null) {
	            regform.setCountry(country);
	            regform.setState(state);
	            regform.setCity(city);
	        } else {
	            model.addAttribute("error", "Invalid country, state, or city.");
	            return "error";
	        }

	        // Handle file upload
	        if (!file.isEmpty()) {
	            String uploadDirectory = "src/main/resources/static/Images";
	            String fileName = file.getOriginalFilename();
	            Path filePath = Paths.get(uploadDirectory, fileName);

	            try {
	                Files.write(filePath, file.getBytes());
	                regform.setFileupload(fileName);
	            } catch (IOException e) {
	                model.addAttribute("error", "Error saving file.");
	                return "error";
	            }
	        }

	        // Save the updated profile details
	        registrationRepository.save(regform);
	        model.addAttribute("message", "Profile updated successfully.");
	        return "redirect:/dashboard";
	    } else {
	        model.addAttribute("error", "User not found.");
	        return "error";
	    }
	}
	@GetMapping("/editprofile")
	public String showEditProfilePage(HttpSession session, Model model) {
	    String username = (String) session.getAttribute("emailid");

	    if (username == null || username.isEmpty()) {
	        model.addAttribute("error", "No email found in session. Please log in.");
	        return "login";
	    }

	    Registration regform = registrationRepository.findByEmail(username);
	    if (regform != null) {
	        model.addAttribute("student", regform);
	        model.addAttribute("countries", countryRepository.findAll());
	        model.addAttribute("states", stateRepository.findAll());
	        model.addAttribute("cities", cityRepository.findAll());
	        return "edit-profile";
	    } else {
	        model.addAttribute("error", "User not found.");
	        return "edit-profile";
	    }
	}


    @RequestMapping("/change-password")
    public String change(Model model, HttpSession session) {
        String email = (String) session.getAttribute("emailid");
        Registration regform = registrationRepository.findByEmail(email);
        
        if (regform != null) {
            model.addAttribute("change", new Registration()); 
            return "change-password"; 
        } else {
            return "error"; 
        }
    }

    @PostMapping("/change-password")
    public String change(HttpSession session, Model model,
                         @RequestParam("currentPassword") String currentPassword,
                         @RequestParam("newPassword") String newPassword,
                         @RequestParam("confirmPassword") String confirmPassword) {

        String email = (String) session.getAttribute("emailid");
        Registration regform = registrationRepository.findByEmail(email);

        if (regform != null && regform.getPassword().equals(currentPassword)) {
            if (newPassword.equals(confirmPassword)) {
                regform.setPassword(newPassword); 
                registrationRepository.save(regform); 
                model.addAttribute("message", "Password changed successfully.");
                return "redirect:/logout"; 
            } else {
                model.addAttribute("error", "New passwords do not match.");
            }
        } else {
            model.addAttribute("error", "Current password is incorrect.");
        }
        
        return "change-password"; 
    }
    
//    @GetMapping("/logout")
//    public String logout(Model model, HttpSession session, Registration user, HttpServletResponse response) {
//        String sess = (String) session.getAttribute("username");
//        
//        if (sess != null) {
//            session.invalidate(); // Invalidate the session
//            
//            // Delete the username cookie
//            Cookie cookie = new Cookie("username", null); // Set value to null to delete
//            cookie.setMaxAge(0); // Set cookie's age to 0 to delete it
//            cookie.setPath("/dashboard"); // Ensure the path is correct for the deletion
//            response.addCookie(cookie);
//        }
//
//        // Add empty Registration objects to the model for the login page
//        model.addAttribute("reg", new Registration());
//        model.addAttribute("login", new Registration());
//
//        return "login"; // Redirect to the login page
//    }
//
//
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate the session
        HttpSession session = request.getSession(false); // Get the current session, if it exists
        if (session != null) {
            session.invalidate(); // Invalidate the session
        }

        // Remove cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    cookie.setValue(null); // Set the value to null
                    cookie.setMaxAge(0); // Set the max age to 0 to delete it
                    cookie.setPath("/"); // Set the path to ensure it is removed
                    response.addCookie(cookie); // Add the cookie back to the response to remove it
                }
            }
        }

        // Redirect to the login page or any other page after logout
        return "redirect:/login"; 
    }

}

