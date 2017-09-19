package com.deshun.springboot.Util;

import lombok.Data;

@Data
public class ResponseInfo<T> {

	private String code;
	
	private String message;
	
	private T data;
}
