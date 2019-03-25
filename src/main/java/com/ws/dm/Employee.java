package com.ws.dm;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Data    // Lombok annotation to create getters, setters, toString(), equals and hash - automatically at runtime?????
@Entity  // jpa to make this object mappable to storage
public class Employee {

	@Id  // JPA primary key
	@GeneratedValue // JPA annotation to map to / create db trigger
	private Long id;
	
	private String name;
	
	private String role;

	public Employee() {
		
	}
		
	public Employee(String name, String role) {
		this.name = name;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}
