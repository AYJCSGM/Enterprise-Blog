package com.test.spring.boot.blog.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

import lombok.Data;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity//实体
public class User implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Id//主键
	@GeneratedValue(strategy=GenerationType.IDENTITY) //自增策略
	private Long id; // 用户的唯一标识

	@NotEmpty(message = "姓名不能为空")
	@Size(min=2, max=20)
	@Column(nullable = false, length = 20) // 映射为字段，值不能为空
	private String name;

	@NotEmpty(message = "邮箱不能为空")
	@Size(max=50)
	@Email(message= "邮箱格式不对" ) 
	@Column(nullable = false, length = 50, unique = true)
	private String email;

	@NotEmpty(message = "账号不能为空")
	@Size(min=3, max=20)
	@Column(nullable = false, length = 20, unique = true)
	private String username; // 用户账号，用户登录时的唯一标识

	@NotEmpty(message = "密码不能为空")
	@Size(max=100)
	@Column(length = 100)
	private String password; // 登录时密码
	
	@Column(length = 200)
	private String avatar; // 头像图片地址
	
	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private List<Authority> authorities;
	
	protected User(){
		 // JPA 的规范要求无参构造函数；设为 protected 防止直接使用
	}
	
	public User(String name, String email, String username, String password,
			String avatar) {
		super();
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.avatar = avatar;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
			//需将 List<Authority> 转成 List<SimpleGrantedAuthority>，否则前端拿不到角色列表名称
			List<SimpleGrantedAuthority> simpleAuthorities = new ArrayList<>();
			for(GrantedAuthority authority : this.authorities){
				simpleAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
			}
			return simpleAuthorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
