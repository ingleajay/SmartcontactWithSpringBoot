package com.smartcontact.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontact.dao.UserRepository;
import com.smartcontact.helper.Message;
import com.smartcontact.models.User;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/home")
    public String home(Model m) {
	 m.addAttribute("title", "Home");
   	 return "home";
    }
	
	@RequestMapping("/signin")
    public String signin(Model m) {
	 m.addAttribute("title", "Login");
   	 return "signin";
    }
	
	@RequestMapping("/register")
    public String signup(Model m) {
	 m.addAttribute("title", "Register");
	 m.addAttribute("user", new User());
   	 return "register";
    }
	
	// processing of register
	@RequestMapping(value="/doregister", method = RequestMethod.POST)
	public String processofregister(@Valid @ModelAttribute("user") User user, BindingResult bresult,
			@RequestParam(value="checkbox", defaultValue="false") boolean checkbox,Model m , 
			HttpSession session) {
		
		try {
			if(!checkbox) {
				System.out.println("You have not agree with terms and conditions");
				throw new Exception("You have not agree with terms and conditions");
			}
			
			if(bresult.hasErrors()) {
				m.addAttribute("user",user);
				return "register";
			}
			
			user.setRole("ROLE_USER");
			user.setActive(true);
			user.setImgeUrl("default.png");
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			User result = this.userRepository.save(user);
			m.addAttribute("user", new User());
			session.setAttribute("message", new Message("alert-success","Successfully register !! "));

			System.out.println("agree"+checkbox);
			System.out.println("user" + user);
			
		} catch (Exception e) {
			e.printStackTrace();
			m.addAttribute("user",user);
			session.setAttribute("message", new Message("alert-danger","Something went wrong !! " + e.getMessage()));
		}
		return "register";
		
	}

}
