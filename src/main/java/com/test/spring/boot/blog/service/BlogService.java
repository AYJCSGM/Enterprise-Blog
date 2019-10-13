package com.test.spring.boot.blog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.test.spring.boot.blog.entity.Blog;
import com.test.spring.boot.blog.entity.Catalog;
import com.test.spring.boot.blog.entity.User;

/**
 * Blog 服务接口.
 * 
 * @since 1.0.0 2017年4月7日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public interface BlogService {
	/**
	 * 保存Blog
	 * @param Blog
	 * @return
	 */
	Blog saveBlog(Blog blog);
	
	/**
	 * 删除Blog
	 * @param id
	 * @return
	 */
	void removeBlog(Long id);
	
	/**
	 * 更新Blog
	 * @param Blog
	 * @return
	 */
	Blog updateBlog(Blog blog);
	
	/**
	 * 根据id获取Blog
	 * @param id
	 * @return
	 */
	Blog getBlogById(Long id);
	
	/**
	 * 根据用户名进行分页模糊查询（最新）
	 * @param user
	 * @return
	 */
	Page<Blog> listBlogsByTitleLike(User user, String title, Pageable pageable);
 
	/**
	 * 根据用户名进行分页模糊查询（最热）
	 * @param user
	 * @return
	 */
	Page<Blog> listBlogsByTitleLikeAndSort(User suser, String title, Pageable pageable);
	
	/**
	 * 阅读量递增
	 * @param id
	 */
	void readingIncrease(Long id);
	
	/**
	 * 发表评论
	 * @param blogId
	 * @param commentContent
	 * @return
	 */
	Blog createComment(Long blogId, String commentContent);
	
	/**
	 * 删除评论
	 * @param blogId
	 * @param commentId
	 * @return
	 */
	void removeComment(Long blogId, Long commentId);
	
	/**
	 * 点赞
	 * @param blogId
	 * @return
	 */
	Blog createVote(Long blogId);

	/**
	 * 取消赞
	 * @param blogId
	 * @param id
	 */
	void removeVote(Long blogId, Long id);

	/**
	 * 根据分类分页博客
	 * @param catalog
	 * @param pageable
	 * @return
	 */
	Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable);

	/**
	 * 用户最热查询
	 * @param user
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<Blog> listBlogsByTitleVoteAndSort(User user, String keyword,
			Pageable pageable);
	
	/**
	 * 首页最热查询
	 * @param user
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<Blog> listBlogsByTitleVoteAndSort(String keyword,Pageable pageable);
	
	/**
	 * 用户最新查询
	 * @param user
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<Blog> listBlogsByTitleVote(User user, String keyword, Pageable pageable);
	
	/**
	 * 首页最新查询
	 * @param user
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	Page<Blog> listBlogsByTitleVote(String keyword, Pageable pageable);

	/**
	 * 分页查询所有博客
	 * @param pageable
	 * @return
	 */
	Page<Blog> listBlogs(Pageable pageable);

	/**
	 * 最新前五
	 * @return
	 */
	List<Blog> listTop5NewestBlogs();

	/**
	 * 最热前五
	 * @return
	 */
	List<Blog> listTop5HotestBlogs();
}
