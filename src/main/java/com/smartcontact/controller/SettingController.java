package com.smartcontact.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontact.dao.UserRepository;
import com.smartcontact.helper.Message;
import com.smartcontact.models.User;

@Controller
@RequestMapping("/user")
public class SettingController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@ModelAttribute
	public void commondata(Model model , Principal principal) {
        String username = principal.getName();
		User user = userRepository.getUserByUserName(username);
		model.addAttribute("user", user);
	
	}

	@GetMapping("/settings")
	public String opensetting(Model m) {
		m.addAttribute("title", "setting");
		return "authuser/settings";
	}
	
	@PostMapping("/changepass")
	public String changepassword(@RequestParam("oldpassword") String oldpass , @RequestParam("newpassword") String newpass ,Principal principal , Model model , HttpSession session) {
		System.out.println("old password"+ oldpass);
		System.out.println("old password" + newpass);
		String username = principal.getName();
		User user = userRepository.getUserByUserName(username);
		if(this.bCryptPasswordEncoder.matches(oldpass, user.getPassword())) {
			user.setPassword(this.bCryptPasswordEncoder.encode(newpass));
			this.userRepository.save(user);
			session.setAttribute("message", new Message("alert-success","Successfully change password !! "));
		}
		else {
			session.setAttribute("message", new Message("alert-danger","old password is incorrect !! "));
			return "redirect:/user/settings";
		}
		return "redirect:/user/index";
	}
}
