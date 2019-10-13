package com.test.spring.boot.blog.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.test.spring.boot.blog.entity.Authority;
import com.test.spring.boot.blog.entity.User;
import com.test.spring.boot.blog.service.AuthorityService;
import com.test.spring.boot.blog.service.UserService;
import com.test.spring.boot.blog.util.ConstraintViolationExceptionHandler;
import com.test.spring.boot.blog.vo.Response;
/**
 * 管理员管理用户控制器
 * @author OJ's big hole
 *
 */
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorityService authorityService;
	
	/**
	 * 查询所有用户
	 * @return
	 */
	@GetMapping
	public ModelAndView list(@RequestParam(value="async",required=false) boolean async,
			@RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
			@RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
			@RequestParam(value="name",required=false,defaultValue="") String name,
			Model model) {
	 
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		Page<User> page = userService.listUsersByNameLike(name, pageable);
		List<User> list = page.getContent();	// 当前所在页面数据列表
		
		model.addAttribute("page", page);
		model.addAttribute("userList", list);
		return new ModelAndView(async==true?"users/list :: #mainContainerRepleace":"users/list", "userModel", model);
	}
	
	
	/**
	 * 获取增加用户界面
	 * @param user
	 * @return
	 */
	@GetMapping("/add")
	public ModelAndView createForm(Model model) {
		model.addAttribute("user", new User(null, null, null, null, null));
		return new ModelAndView("users/add", "userModel", model);
	}
	
	
	
	/**
	 * 保存或者修改用户
	 * @param user
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Response> saveOrUpdate(User user,Long authorityId){
		
		List<Authority> authorities=new ArrayList<Authority>();
		authorities.add(authorityService.getAuthorityById(authorityId));
		user.setAuthorities(authorities);

		try{
			userService.saveOrUpdateUser(user);
		}catch(ConstraintViolationException e){
			 return ResponseEntity.ok().body(new Response(false	, ConstraintViolationExceptionHandler.getMessage(e)));
		}
		
		return ResponseEntity.ok().body(new Response(true, "处理成功",user));
	}
	
	/**
	 * 获取用户编辑页面
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/{id}")
	public ModelAndView editGetUserInfo(@PathVariable(value="id") Long id,Model model){
		User user=userService.getUserById(id);
		model.addAttribute("user", user);
		return new ModelAndView("users/add", "userModel", model);
	}
	
	/**
	 * 删除用户
	 * @param id
	 * @param model
	 * @return
	 */
	@DeleteMapping("/{id}")
	public Response deleteUser(@PathVariable(value="id") Long id,Model model){
		userService.removeUser(id);
		return new Response(true, "删除成功");
	}
	
}
