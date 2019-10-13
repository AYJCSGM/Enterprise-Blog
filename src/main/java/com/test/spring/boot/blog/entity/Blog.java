package com.test.spring.boot.blog.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotEmpty;

import com.github.rjeschke.txtmark.Processor;

/**
 * Blog 实体
 * 
 * @since 1.0.0 2017年4月7日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
@Entity // 实体
@Data
public class Blog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id // 主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
	private Long id; // 用户的唯一标识
	
	@NotEmpty(message = "标题不能为空")
	@Size(min=2, max=50)
	@Column(nullable = false, length = 50) // 映射为字段，值不能为空
	private String title;
	
	@NotEmpty(message = "摘要不能为空")
	@Size(min=2, max=300)
	@Column(nullable = false) // 映射为字段，值不能为空
	private String summary;

	@Lob  // 大对象，映射 MySQL 的 Long Text 类型
	@Basic(fetch=FetchType.LAZY) // 懒加载
	@NotEmpty(message = "内容不能为空")
	@Size(min=2)
	@Column(nullable = false) // 映射为字段，值不能为空
	private String content;
	
	@Lob  // 大对象，映射 MySQL 的 Long Text 类型
	@Basic(fetch=FetchType.LAZY) // 懒加载
	@Size(min=2)
	@Column(nullable = false) // 映射为字段，值不能为空
	private String htmlContent; // 将 md 转为 html

	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="create_time") // 映射为字段，值不能为空
	@CreationTimestamp  // 由数据库自动创建时间
	private Timestamp createTime;

	@Column(name="readSize")
	private Integer readSize = 0; // 访问量、阅读量
	 
	@Column(name="commentSize")
	private Integer commentSize = 0;  // 评论量

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "blog_comment", joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"))
	private List<Comment> comments;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "blog_vote", joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "vote_id", referencedColumnName = "id"))
	private List<Vote> votes;

	@Column(name="voteSize")
	private Integer voteSize=0;//点赞量
	
	@Column(name="tags",length=100)
	private String tags;//标签
	
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name="catalog_id")
	private Catalog catalog;
	
	protected Blog() {
	
	}
	
	public Blog(String title, String summary,String content) {
		this.title = title;
		this.summary = summary;
		this.content = content;
	}
	
	
	public void setContent(String content) {
		this.content = content;
		this.htmlContent = Processor.process(content);
	}
	
	/**
	 * 添加评论
	 * @param comment
	 */
	public void addComment(Comment comment) {
		this.comments.add(comment);
		this.commentSize = this.comments.size();
	}
	/**
	 * 删除评论
	 * @param comment
	 */
	public void removeComment(Long commentId) {
		for (int index=0; index < this.comments.size(); index ++ ) {
			if (comments.get(index).getId() == commentId) {
				this.comments.remove(index);
				break;
			}
		}
		this.commentSize = this.comments.size();
	}
	
	/**
	 * 点赞
	 * @param vote
	 * @return
	 */
	public boolean addVote(Vote vote) {
		boolean isExist = false;
		// 判断重复
		for (int index=0; index < this.votes.size(); index ++ ) {
			if (this.votes.get(index).getUser().getId() == vote.getUser().getId()) {
				isExist = true;
				break;
			}
		}
		
		if (!isExist) {
			this.votes.add(vote);
			this.voteSize = this.votes.size();
		}

		return isExist;
	}
	/**
	 * 取消点赞
	 * @param voteId
	 */
	public void removeVote(Long voteId) {
		for (int index=0; index < this.votes.size(); index ++ ) {
			if (this.votes.get(index).getId() == voteId) {
				this.votes.remove(index);
				break;
			}
		}
		
		this.voteSize = this.votes.size();
	}
	
}
