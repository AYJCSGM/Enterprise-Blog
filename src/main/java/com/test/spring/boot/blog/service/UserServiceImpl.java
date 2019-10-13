package com.test.spring.boot.blog.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.spring.boot.blog.dao.UserRepository;
import com.test.spring.boot.blog.entity.User;

/**
 * 用户服务接口实现
 * @author OJ's big hole
 *
 */
@Service
public class UserServiceImpl implements UserService,UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Transactional
	@Override
	public User saveOrUpdateUser(User user) {		
		return userRepository.save(user);
	}

	@Transactional
	@Override
	public void removeUser(Long id) {
		userRepository.delete(id);
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.getOne(id);
	}

	@Override
	public Page<User> listUsersByNameLike(String name, Pageable pageable) {
		name="%" +name +"%";
		return userRepository.findByNameLike(name, pageable);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		return userRepository.findByUsername(username);
	}
	
	@Override
	public List<User> listUsersByUsernames(Collection<String> usernames) {
		return userRepository.findByUsernameIn(usernames);
	}

	@Override
	public User findByUsername(String username) {
		return (User) userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
