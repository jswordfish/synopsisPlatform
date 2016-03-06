package com.v2tech.base;

public class V2GenericException extends RuntimeException{

	public V2GenericException(){
		super();
	}
	
	public V2GenericException(String message){
		super(message);
	}
	
	public V2GenericException(String message, Throwable t){
		super(message, t);
	}

}

