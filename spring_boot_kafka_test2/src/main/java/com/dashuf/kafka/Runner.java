package com.dashuf.kafka;

import com.dashuf.support.config.EnableService;
import com.dashuf.support.starter.Starter;

@EnableService
public class Runner {

	public static void main(String[] args) {
		Starter.run(args);
	}
}
