package com.chhabinath.userdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Pageable;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


import com.chhabinath.userdata.entity.Users;
import com.chhabinath.userdata.repository.UserRepository;

@Controller
public class UserController {

    private final HomeController homeController;

	@Autowired
	private UserRepository userRepository;

    UserController(HomeController homeController) {
        this.homeController = homeController;
    }

	@GetMapping("/register")
	public String showForm(Model model) {
		model.addAttribute("users", new Users());
		return "register";
	}

	@PostMapping("/register")
	public String submitForm(@ModelAttribute("users") Users users) {
		userRepository.save(users);
		return "register_success";
	}

//	@GetMapping("/users")
//	public String getAllUsers(Model model) {
//		List<Users> users = userRepository.findAll();
//		model.addAttribute("users", users);
//		return "users"; // maps to users.html
//	}
	
//	@GetMapping("/users")
//	public String viewUsers(Model model, @RequestParam(required = false) String keyword) {
//	    List<Users> users;
//	    if (keyword != null && !keyword.isEmpty()) {
//	        users = userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
//	    } else {
//	        users = userRepository.findAll();
//	    }
//	    model.addAttribute("users", users);
//	    model.addAttribute("keyword", keyword);
//	    return "users";
//	}
//	
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
	    userRepository.deleteById(id);
	    return "redirect:/users";
	}

	@GetMapping("/users/edit/{id}")
	public String editUserForm(@PathVariable Long id, Model model) {
	    Users user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
	    model.addAttribute("user", user);
	    return "edit_user";
	}

	@PostMapping("/users/update")
	public String updateUser(@ModelAttribute("user") Users user) {
	    userRepository.save(user);
	    return "redirect:/users";
	}

	@GetMapping("/users")
	public String viewUsersPage(
	        Model model,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size,
	        @RequestParam(defaultValue = "id") String sortField,
	        @RequestParam(defaultValue = "asc") String sortDir,
	        @RequestParam(required = false) String keyword) {

	    Pageable pageable = PageRequest.of(page, size,
	            sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());

	    Page<Users> pageUsers;

	    if (keyword != null && !keyword.isEmpty()) {
	        pageUsers = userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword, pageable);
	    } else {
	        pageUsers = userRepository.findAll(pageable);
	    }

	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", pageUsers.getTotalPages());
	    model.addAttribute("totalItems", pageUsers.getTotalElements());
	    model.addAttribute("users", pageUsers.getContent());
	    model.addAttribute("sortField", sortField);
	    model.addAttribute("sortDir", sortDir);
	    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
	    model.addAttribute("keyword", keyword);

	    return "users";
	}



}