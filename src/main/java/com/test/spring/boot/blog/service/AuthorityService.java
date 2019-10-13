package com.test.spring.boot.blog.service;

import com.test.spring.boot.blog.entity.Authority;

/**
 * Authority服务接口
 * @author OJ's big hole
 *
 */
public interface AuthorityService {
	
	public Authority getAuthorityById(Long id);
	
}
