package com.test.spring.boot.blog.util;

public class MyException extends Exception{

	private static final long serialVersionUID = 1L;

	public MyException (String message){
		super(message);
	}
}
