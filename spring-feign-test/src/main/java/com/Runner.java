package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableFeignClients
public class Runner {
	
	@Autowired
	private TestSao testSao;

	public static void main(String[] args) {
		
		ConfigurableApplicationContext configurableApplicationContext=SpringApplication.run(Runner.class, args);
		Runner runner=configurableApplicationContext.getBean(Runner.class);
		String str=runner.testSao.auditFindAll("{}");
		System.out.println(str);
	}
}
