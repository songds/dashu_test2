package com.deshun.springboot.Util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import com.deshun.springboot.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired  
    private MyFilterSecurityInterceptor mySecurityFilter;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	//http://localhost:8080/login 输入正确的用户名密码 并选中remember-me 则登录成功，转到index夜面
	//再次访问index页面无需登录直接访问
	//访问http://localhost:8080/home 不拦截，直接访问
	//访问http://localhost:8080/hello需要登录验证后，且具备"ADMIN"权限hasAuthority("ADMIN")才可以访问
	 
	@Override  
    public AuthenticationManager authenticationManagerBean() throws Exception {  
		 return super.authenticationManagerBean();  
    } 
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.addFilterBefore(mySecurityFilter, FilterSecurityInterceptor.class)//在正确的位置添加我们自定义的过滤器
		.authorizeRequests()
		.antMatchers("/home","/css/**","/image/**","/js/**").permitAll()//访问：/home无需登录认证权限
		.antMatchers("/html/page/**").permitAll()
		.antMatchers("/html/select/**").permitAll()
		.antMatchers("/api/**").permitAll()
		.antMatchers("/html/saveUser").permitAll()
		.anyRequest().authenticated() //其他所有资源都需要认证，登录后访问
		//.antMatchers("/hellow").hasAnyAuthority("ADMIN")//登录后之后拥有“ADMIN”权限才可以访问/hello方法，否则系统会出现“403”权限不足的提示
		.and().formLogin()
		.loginPage("/login") //指定登录页是"/login"
		.permitAll()
		.successHandler(loginSuccessHandler())//登录成功后可使用loginSuccessHandler()存储用户信息，可选
		.and().logout().logoutSuccessUrl("/home") //退出登录后的默认网址"/home"
		.permitAll().invalidateHttpSession(true)
		.and()
		.rememberMe()//登录后记住用户，下次自动登录，数据库中必须存在名为persistent_logins的表
		.tokenValiditySeconds(1209600);
		//关闭crsf
		http.csrf().disable();
	}
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		//指定密码加密所使用的加密器为passwordEncoder()
		//需要将密码加密后写入数据库
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
		auth.eraseCredentials(false);
	}
	
	
	@Bean  
    public BCryptPasswordEncoder passwordEncoder() {  
        return new BCryptPasswordEncoder(4);  
    }  
  
    @Bean  
    public LoginSuccessHandler loginSuccessHandler(){  
        return new LoginSuccessHandler();  
    }  
    
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
        //可以仿照上面一句忽略静态资源
        
   }
}
