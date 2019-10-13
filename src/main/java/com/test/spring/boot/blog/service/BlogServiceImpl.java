package com.test.spring.boot.blog.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.test.spring.boot.blog.dao.BlogRepository;
import com.test.spring.boot.blog.entity.Blog;
import com.test.spring.boot.blog.entity.Catalog;
import com.test.spring.boot.blog.entity.Comment;
import com.test.spring.boot.blog.entity.User;
import com.test.spring.boot.blog.entity.Vote;

/**
 * Blog 服务.
 * 
 * @since 1.0.0 2017年4月7日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogRepository blogRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.waylau.spring.boot.blog.service.BlogService#saveBlog(com.waylau.spring
	 * .boot.blog.domain.Blog)
	 */
	@Transactional
	@Override
	public Blog saveBlog(Blog blog) {
		return blogRepository.save(blog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.waylau.spring.boot.blog.service.BlogService#removeBlog(java.lang.
	 * Long)
	 */
	@Transactional
	@Override
	public void removeBlog(Long id) {
		blogRepository.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.waylau.spring.boot.blog.service.BlogService#updateBlog(com.waylau
	 * .spring.boot.blog.domain.Blog)
	 */
	@Transactional
	@Override
	public Blog updateBlog(Blog blog) {
		return blogRepository.save(blog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.waylau.spring.boot.blog.service.BlogService#getBlogById(java.lang
	 * .Long)
	 */
	@Override
	public Blog getBlogById(Long id) {
		return blogRepository.findOne(id);
	}

	@Override
	public Page<Blog> listBlogsByTitleLike(User user, String title,
			Pageable pageable) {
		// 模糊查询
		title = "%" + title + "%";
		Page<Blog> blogs = blogRepository
				.findByUserAndTitleLikeOrderByCreateTimeDesc(user, title,
						pageable);
		return blogs;
	}

	@Override
	public Page<Blog> listBlogsByTitleLikeAndSort(User user, String title,
			Pageable pageable) {
		// 模糊查询
		title = "%" + title + "%";
		Page<Blog> blogs = blogRepository.findByUserAndTitleLike(user, title,
				pageable);
		return blogs;
	}

	@Override
	public void readingIncrease(Long id) {
		Blog blog = blogRepository.findOne(id);
		blog.setReadSize(blog.getReadSize() + 1);
		blogRepository.save(blog);
	}

	@Override
	public Blog createComment(Long blogId, String commentContent) {
		Blog originalBlog = blogRepository.findOne(blogId);
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Comment comment = new Comment(user, commentContent);
		originalBlog.addComment(comment);
		return blogRepository.save(originalBlog);
	}

	@Override
	public void removeComment(Long blogId, Long commentId) {
		Blog originalBlog = blogRepository.findOne(blogId);
		originalBlog.removeComment(commentId);
		blogRepository.save(originalBlog);
	}

	@Override
	public Blog createVote(Long blogId) {
		Blog originalBlog = blogRepository.findOne(blogId);
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Vote vote = new Vote(user);
		boolean isExist = originalBlog.addVote(vote);
		if (isExist) {
			throw new IllegalArgumentException("该用户已经点过赞了");
		}
		return blogRepository.save(originalBlog);
	}

	@Override
	public void removeVote(Long blogId, Long voteId) {
		Blog originalBlog = blogRepository.findOne(blogId);
		originalBlog.removeVote(voteId);
		blogRepository.save(originalBlog);
	}

	@Override
	public Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable) {
		Page<Blog> blogs = blogRepository.findByCatalog(catalog, pageable);
		return blogs;
	}

	@Override
	public Page<Blog> listBlogsByTitleVoteAndSort(User user, String title,
			Pageable pageable) {
		// 模糊查询
		title = "%" + title + "%";
		Page<Blog> blogs = blogRepository.findByUserAndTitleLike(user, title,
				pageable);
		return blogs;
	}

	@Override
	public Page<Blog> listBlogsByTitleVote(User user, String title,
			Pageable pageable) {
		// 模糊查询
		title = "%" + title + "%";
		String tags = title;
		Page<Blog> blogs = blogRepository
				.findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(
						title, user, tags, user, pageable);
		return blogs;
	}

	@Override
	public Page<Blog> listBlogsByTitleVoteAndSort(String title,
			Pageable pageable) {
		// 模糊查询
		title = "%" + title + "%";
		Page<Blog> blogs = blogRepository.findByTitleLike(title, pageable);
		return blogs;
	}

	@Override
	public Page<Blog> listBlogsByTitleVote(String title, Pageable pageable) {
		// 模糊查询
		title = "%" + title + "%";
		Page<Blog> blogs = blogRepository.findByTitleLike(title, pageable);
		return blogs;
	}

	@Override
	public Page<Blog> listBlogs(Pageable pageable) {
		Page<Blog> pageble=blogRepository.findAll(pageable);
		return pageble;
	}

	@Override
	public List<Blog> listTop5NewestBlogs() {
		return blogRepository.findByBlogListTop5NewestBlogs();
	}

	@Override
	public List<Blog> listTop5HotestBlogs() {
		return blogRepository.findByBlogListTop5HotestBlogs();
	}
}
