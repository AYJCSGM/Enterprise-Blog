package com.test.spring.boot.blog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.test.spring.boot.blog.entity.Authority;
import com.test.spring.boot.blog.entity.User;
import com.test.spring.boot.blog.service.AuthorityService;
import com.test.spring.boot.blog.service.UserService;
import com.test.spring.boot.blog.util.MyException;
/**
 * 主页导航控制器
 * @author OJ's big hole
 *
 */
@Controller
public class MainController {
	
	private static final Long ROLE_USER_AUTHORITY_ID = 2L;

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorityService authorityService;

	/**
	 * 根跳转
	 * @return
	 */
	@GetMapping("/")
	public String root(){
		return "redirect:/index";
	}
	
	/**
	 * 主页跳转
	 * @return
	 */
	@GetMapping("/index")
	public String index(){
		return "redirect:/blogs";
	}
	
	/**
	 * 登录跳转
	 * @return
	 */
	@GetMapping("/login")
	public String login(){
		return "login";
	}
	
	/**
	 * 登录失败跳转
	 * @param model
	 * @return
	 */
	@PostMapping("/login-error")
	public String loginError(Model model){
		model.addAttribute("loginError", true);
		model.addAttribute("errorMsg", "登录失败,用户名或者密码错误");
		return "login";
	}
	
	/**
	 * 注册跳转
	 * @return
	 */
	@GetMapping("/register")
	public String register(){
		return "register";
	}
	
	/**
	 * 注册用户
	 * @param user
	 * @param result
	 * @param redirect
	 * @return
	 * @throws MyException 
	 */
	@PostMapping("/register")
	public String registerUser(User user) throws MyException {
		
		if(userService.findByUsername(user.getUsername())!=null){
			throw new MyException("*用户名已存在,请更换用户名");
		}
		
		if(userService.findByEmail(user.getEmail())!=null){
			throw new MyException("*邮箱已存在,请更换邮箱");
		}
		
		List<Authority> authorities=new ArrayList<Authority>();
		authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
		user.setAuthorities(authorities);
		
		userService.saveOrUpdateUser(user);
		return "redirect:/login";
	}
}

