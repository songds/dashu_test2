package com.dashuf.kafka;

import com.dashuf.support.config.EnableMicroService;
import com.dashuf.support.starter.Starter;

@EnableMicroService
public class Runner {

	public static void main(String[] args) {
		Starter.run(args);
	}
}
