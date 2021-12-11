package com.smartcontact.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontact.dao.UserRepository;
import com.smartcontact.helper.Message;
import com.smartcontact.models.User;
import com.smartcontact.services.EmailServices;

@Controller
public class ForgotController {
	
	@Autowired
	private EmailServices emailServices;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@RequestMapping("/forgot")
	public String openEmailForm() {
		return "forgot";
	}
	
	@PostMapping("/sendotp")
	public String sendotptoemail(@RequestParam("email") String email , HttpSession session) {
		System.out.println(email);
		Random random = new Random(1000);
		int otp = random.nextInt(9999);
		System.out.println(otp);
		String subject = "OTP from SCM";
		String msg = 
				"" + "<div style='border:1px solid #e2e2e2; padding:20px'> " 
		           + "<h1>" + "OTP is " + "<b>" + otp + "</b>" + "</h1>" + "</div>";
		String to = email;
		boolean flag = this.emailServices.sendEmail(subject,msg,to);
		if(flag) {
			session.setAttribute("myotp", otp);
			session.setAttribute("email", email);
			session.setAttribute("message", new Message("alert-success","Congraluation ! We have sent OTP to your email"));
			return "verifyotp";
		}else {
			session.setAttribute("message", new Message("alert-danger","Check your email again !!"));
			return "forgot";
		}
		
	}
	
	@PostMapping("/verifyotp")
	public String verifyotp(@RequestParam("otp") int otp , HttpSession session) {
		int myotp = (int)session.getAttribute("myotp");
		String email = (String) session.getAttribute("email");
		
		if(myotp==otp) {
			User user = this.userRepository.getUserByUserName(email);
			if(user == null) {
				// error
				session.setAttribute("message", new Message("alert-danger","User does not exists with this email!!"));
				return "forgot";
			}else {
				// send change pass form
				
			}
			return "changepass";
		}
		else {
			session.setAttribute("message", new Message("alert-danger","You have enterd wrong otp!!"));
			return "verifyotp";
		}
	}
	
	// chnage password
	
	@PostMapping("/changepassword")
	public String changepassword(@RequestParam("newpass") String newpassword , HttpSession session)
	{
		String email = (String) session.getAttribute("email");
		User user = this.userRepository.getUserByUserName(email);
        user.setPassword(this.bCryptPasswordEncoder.encode(newpassword));
        this.userRepository.save(user);
        return "redirect:/signin?change=password changed successfully !! ";
	}
}

