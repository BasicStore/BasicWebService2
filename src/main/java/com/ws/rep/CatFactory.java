package com.ws.rep;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ws.dm.Cat;
import com.ws.dm.Employee;

@Service
public class CatFactory {
	
	private static List<Employee> empList = getAllDummyEmployees();
	
	public CatFactory() {
		
	}

	
	public List<Employee> getAllEmployees() {
		
		
		return null;
	}
	
	
	public List<Employee> getAllEmployees(int val) {
		
		
		return null;
	}
	
	
	public void activateEmployee() {
		
	}
		
	
	public List<Cat> getAllCats() {
		return Arrays.asList(new Cat[] {
				new Cat(1L, "Sally"),
				new Cat(2L, "Chantal")
		});
	}
	
	
	
	public static List<Employee> getAllDummyEmployees() {
		return Arrays.asList(new Employee[] {
				new Employee(11L, "name1", "role1"),
				new Employee(21L, "name2", "role2"),
				new Employee(31L, "name3", "role3"),
				new Employee(41L, "name4", "role4"),
				new Employee(51L, "name5", "role5")
		});
	}
	
	
	
	
}
