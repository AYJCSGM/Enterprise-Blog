package com.test.spring.boot.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 返回对象
 * @author OJ's big hole
 *
 */
@Data
@AllArgsConstructor
public class Response {
	private boolean success;//处理是否成功
	private String message;//处理后的消息提示
	private Object body;//返回的数据
	
	
	
	public Response(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
}
