package com.test.spring.boot.blog.vo;


import java.io.Serializable;

/**
 * Tag 值对象.
 * 
 * @since 1.0.0 2017年4月13日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
public class TagVO implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Long count;
	
	public TagVO(String name, Long count) {
		this.name = name;
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
 
}
