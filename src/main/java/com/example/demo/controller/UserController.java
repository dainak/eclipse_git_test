package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/users")
	public String index(
			@RequestParam(name = "keyword", defaultValue = "") String keyword,
			Model model) {

		List<User> userList = null;

		if (keyword.length() > 0) {
			userList = userRepository.findByNameContaining(keyword);
		} else {
			userList = userRepository.findAll();
		}
		model.addAttribute("users", userList);
		model.addAttribute("keyword", keyword);

		return "users";
	}

	@GetMapping("/users/add")
	public String create() {
		return "addUser";
	}

	@PostMapping("/users/add")
	public String store(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "password", defaultValue = "") String password,
			Model model) {

		User user = new User(name, email, password);
		userRepository.save(user);

		return "redirect:/users";
	}

	@GetMapping("/users/{id}/edit")
	public String edit(
			@PathVariable("id") Integer id,
			Model model) {

		model.addAttribute("id", id);

		return "editUser";
	}

	@PostMapping("/users/{id}/edit")
	public String update(
			@PathVariable("id") Integer id,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "password", defaultValue = "") String password,
			Model model) {

		User user = new User(id, name, email, password);
		userRepository.save(user);

		return "redirect:/users";
	}

	@PostMapping("/users/{id}/delete")
	public String delete(
			@PathVariable("id") Integer id) {

		userRepository.deleteById(id);

		return "redirect:/users";
	}
}
