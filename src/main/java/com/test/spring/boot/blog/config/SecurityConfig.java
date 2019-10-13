package com.test.spring.boot.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 安全配置类
 * @author OJ's big hole
 *
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法安全设置
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private static final String KEY = "JC";

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Bean  
    public PasswordEncoder passwordEncoder() {  
        return new BCryptPasswordEncoder();   // 使用 BCrypt 加密
    }  
	
	
	@Bean  
    public AuthenticationProvider authenticationProvider() {  
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder); // 设置密码加密方式
        return authenticationProvider;  
    }  
	
	
	/**
	 * 自定义配置
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.authorizeRequests()
			.antMatchers("/css/**","/js/**","/fonts/**","/index").permitAll()//这些路径都可以访问  
			.antMatchers("/admins/**").hasRole("ADMIN")//需要相应的角色才能访问 
			.and()
			.formLogin()//基于form表单登录验证  
			.loginPage("/login").failureForwardUrl("/login-error")//自定义登录界面
			.and()
			.rememberMe().key(KEY)//启用 remember me
			.and().exceptionHandling().accessDeniedPage("/403")// 处理异常，拒绝访问就重定向到 403 页面
			.and()
			.csrf().disable();
	}
	
	/**
	 * 认证信息管理
	 * @param auth
	 * @throws Exception
	 */
	@Autowired  
	public void configureGloble(AuthenticationManagerBuilder auth) throws Exception{  
	    auth.userDetailsService(userDetailsService);
	    auth.authenticationProvider(authenticationProvider());
	}  
}
