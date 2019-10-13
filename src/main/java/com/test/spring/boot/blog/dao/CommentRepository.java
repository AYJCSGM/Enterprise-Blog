package com.test.spring.boot.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.spring.boot.blog.entity.Comment;

/**
 * Comment 仓库.
 *
 * @since 1.0.0 2017年4月7日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
public interface CommentRepository extends JpaRepository<Comment, Long>{
 
}
