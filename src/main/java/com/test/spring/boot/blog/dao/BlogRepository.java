package com.test.spring.boot.blog.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.test.spring.boot.blog.entity.Blog;
import com.test.spring.boot.blog.entity.Catalog;
import com.test.spring.boot.blog.entity.User;

/**
 * Blog 仓库.
 *
 * @since 1.0.0 2017年4月7日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public interface BlogRepository extends JpaRepository<Blog, Long> {
	/**
	 * 根据用户名分页查询用户列表
	 * 
	 * @param user
	 * @param title
	 * @param pageable
	 * @return
	 */
	Page<Blog> findByUserAndTitleLikeOrderByCreateTimeDesc(User user,
			String title, Pageable pageable);

	/**
	 * 根据用户名分页查询用户列表
	 * 
	 * @param user
	 * @param title
	 * @param sort
	 * @param pageable
	 * @return
	 */
	Page<Blog> findByUserAndTitleLike(User user, String title, Pageable pageable);

	/**
	 * 根据分类分页出Blog列表
	 * 
	 * @param catalog
	 * @param pageable
	 * @return
	 */
	Page<Blog> findByCatalog(Catalog catalog, Pageable pageable);
	
	/**
	 * 用户博客关键字查找
	 * @param title
	 * @param user
	 * @param tags
	 * @param user2
	 * @param pageable
	 * @return
	 */
	Page<Blog> findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(
			String title, User user, String tags, User user2, Pageable pageable);
	
	/**
	 * 所有博客关键字查找
	 * @param title
	 * @param pageable
	 * @return
	 */
	Page<Blog> findByTitleLike(String title, Pageable pageable);
	
	/**
	 *  所有博客关键字(带标签)查找
	 * @param title
	 * @param tags
	 * @param pageable
	 * @return
	 */
	Page<Blog> findByTitleLikeOrTagsLikeOrderByCreateTimeDesc(String title,
			String tags, Pageable pageable);
	
	/**
	 * 最新前五博客
	 * @return
	 */
	@Query(value = "select * FROM  blog order by create_time desc limit 0,5", nativeQuery = true)
	List<Blog> findByBlogListTop5NewestBlogs();
	
	/**
	 * 最热前五博客
	 * @return
	 */
	@Query(value = "select * FROM  blog order by read_size desc,comment_size desc,vote_size desc,create_time desc limit 0,5", nativeQuery = true)
	List<Blog> findByBlogListTop5HotestBlogs();

}
