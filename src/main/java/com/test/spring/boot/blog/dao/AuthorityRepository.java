package com.test.spring.boot.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.spring.boot.blog.entity.Authority;

/**
 * 权限仓库
 * @author OJ's big hole
 *
 */
public interface AuthorityRepository extends JpaRepository<Authority,Long>{

}
