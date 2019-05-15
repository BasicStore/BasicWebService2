package com.ws.dm;
import lombok.Data;

@Data 
public class Cat {

	private Long id;
	private String firstName;
	
	
	public Cat() {
		// TODO Auto-generated constructor stub
	}


	public Cat(Long id, String firstName) {
		super();
		this.id = id;
		this.firstName = firstName;
	}

	
	
	
}
