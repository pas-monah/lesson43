package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;

@Controller
public class MyController {
	private List<User> users = new ArrayList<>();

	@GetMapping("/info")
	public String getInfo(@RequestParam(value = "param") String param, Model model) {
		model.addAttribute("parametr", param);
		return "info";
	}

	@GetMapping("/create_user")
	public String createUser() {
		return "user_form";
	}

	@PostMapping("/add_user")
	public String addUser(@ModelAttribute(name = "user") User user) {
		users.add(user);
		user.setId(users.stream().max((u1, u2) -> u1.getId() - u2.getId()).orElseGet(() -> new User()).getId() + 1);
		return "redirect:/users";
	}

	@GetMapping("/users")
	public String users(Model model) {
		model.addAttribute("users", users);
		return "users";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(name = "id") int id) {
		users.removeIf(u -> u.getId() == id);
		return "redirect:/users";
	}

	@GetMapping("/update/{id}")
	public String update(@PathVariable(name = "id") int id, Model model) {
		model.addAttribute("user", users.stream().filter(u -> u.getId() == id).findFirst().get());
		return "user_update";
	}
	
	@PostMapping("/user_update")
	public String userUpdate(@ModelAttribute(name="user") User user) {
		int index = 0;
		for(int i = 1; i<users.size(); i++) {
			if(users.get(i).getId()==user.getId()) {
				index = i;
				break;
			}
		}
		if(users.get(index).getId()==user.getId()) {
			users.get(index).setName(user.getName());
			users.get(index).setAge(user.getAge());
			users.get(index).setGender(user.getGender());
		}
		return "redirect:/users";
	}
}
