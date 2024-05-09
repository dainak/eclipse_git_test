package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {

	@Autowired
	HttpSession session;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Account account;

	@GetMapping({ "/login", "logout" })
	public String index() {
		session.invalidate();
		return "login";
	}

	@PostMapping("/login")
	public String login(
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "password", defaultValue = "") String password,
			Model model) {

		List<User> userList = userRepository.findByEmailAndPassword(email, password);

		if (userList.size() == 0) {
			model.addAttribute("error", "メールアドレスとパスワードが一致しませんでした");
			return "/login";
		} else {
			String name = userList.get(0).getName();
			account.setName(name);
			
			return "redirect:/users";
		}
	}
}
