package com.ws.ctrl;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ws.dm.Cat;
import com.ws.dm.Employee;
import com.ws.ex.EmployeeNotFoundException;
import com.ws.rep.CatFactory;
import com.ws.rep.EmployeeRepository;


@RestController
@RequestMapping("/api/path")
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	private final EmployeeRepository repository;

	@Autowired
	private final CatFactory catFactory;
	
	
	EmployeeController(EmployeeRepository repository, CatFactory catFactory) {
	this.repository = repository;
	this.catFactory = catFactory;
}
	
	
	
//  curl -v localhost:8081/api/path/cats
	@GetMapping("/cats")
	List<Cat> getCats() {
		List<Cat> catList = catFactory.getAllCats();
		
		catList.forEach(c-> logger.info("cat: " + c.toString()));
		
		
		
		
		
		return catList; 
	}
	
	
	
	
//  curl -v localhost:8081/api/path/employees
	@GetMapping("/employees")
	//List<Employee> getEmployees(@PathVariable Long id) {
	List<Employee> getEmployees() {
		
		//logger.info("employee id given: " + id);
		
		return catFactory.getAllDummyEmployees();
		
		//return makeEmployee();
		//return repository.findAll();
	}
	
	
	
	
	private List<Employee> makeEmployee() {
		List<Employee> emplList = Arrays.asList(new Employee[] { 
						new Employee(11L, "name1", "role1"),
						new Employee(21L, "name2", "role2"),
						new Employee(31L, "name3", "role3")
						});
		//return emplList.stream().collect(Collectors.toSet());
		return emplList;
	}
	
	
	
//	@GetMapping("/employees")
//	Resources<Resource<Employee>> getEmployees() { // returns collection of Employee resources......
//		List<Resource<Employee>> employees = repository.findAll().stream()
//			.map(employee -> new Resource<>(employee,  // effectively same links as one() implementation below
//				linkTo(methodOn(EmployeeController.class).getEmployee(employee.getId())).withSelfRel(),
//				linkTo(methodOn(EmployeeController.class).getEmployees()).withRel("employees")))
//			.collect(Collectors.toList());
//		return new Resources<>(employees,
//			linkTo(methodOn(EmployeeController.class).getEmployees()).withSelfRel());
//	}
	
	
	@PostMapping("/add/employee")
	Employee newEmployee(@RequestBody Employee newEmployee) {
		return repository.save(newEmployee);
	}
	
//     curl -v localhost:8081/api/path/employees/900
	@GetMapping("/employees/{id}")
	Employee getEmployee(@PathVariable Long id) {
		logger.info("employee id given: " + id);
		return repository.findById(id)
			.orElseThrow(() -> new EmployeeNotFoundException(id));
	}
	
//  curl -v localhost:8081/api/path/employees/900/Sally
	@GetMapping("/employees/{id}/{name}") 
	Employee getEmployeeWithName(@PathVariable Long id, @PathVariable String name) throws Throwable {
 			logger.info("employee id given: " + id + " ==> and name = " + name);
 			return repository.findById(id)
 				.orElseThrow(() -> new EmployeeNotFoundException(id));
	}
	
	
	
	
	
//	@GetMapping("/employees/{id}")
//	Resource<Employee> getEmployee(@PathVariable Long id) {     // return container for the data AND and collection of links
//		Employee employee = repository.findById(id)
//			.orElseThrow(() -> new EmployeeNotFoundException(id));
//		return new Resource<>(employee,
//			linkTo(methodOn(EmployeeController.class).getEmployee(id)).withSelfRel(),  // asks spring hateoas to build a 'self' link to one() method
//			linkTo(methodOn(EmployeeController.class).getEmployees()).withRel("employees")); // asks spring hateoas to build a link to the all() method
//		                                                                            // and call it 'employees'
//	} // this will output in HAL format (a lightweight mediatype)
	
	
	
	// curl -X PUT localhost:8081/api/path/employees/3 -H 'Content-type:application/json' -d '{"name": "XXXSamwise Gamgee", "role": "XXXgardener"}'
	@PutMapping("/employees/{id}")
	Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

		logger.info("UPDATED EMPLOYEE: " + newEmployee.toString());
		
		return new Employee(id, newEmployee.getName(), newEmployee.getRole());
		
		
//		return repository.findById(id)
//			.map(employee -> {
//				employee.setName(newEmployee.getName());
//				employee.setRole(newEmployee.getRole());
//				return repository.save(employee);
//			})
//			.orElseGet(() -> {
//				newEmployee.setId(id);
//				return repository.save(newEmployee);
//			});
	}

	
	
	@DeleteMapping("/employees/{id}")
	void deleteEmployee(@PathVariable Long id) {
		repository.deleteById(id);
	}


	
	// curl -X PUT localhost:8081/api/path/cats/1 -H 'Content-type:application/json' -d '{"firstName": "CATXXXSamwise Gamgee"}'	
	@PutMapping("/cats/{id}")
	Cat replaceCat(@RequestBody Cat updatedCat, @PathVariable Long id) {

		logger.info("UPDATED CAT: id=" + id + " " + updatedCat.toString());
		
		return new Cat(1L, "Shiela");
		
//		return repository.findById(id)
//			.map(employee -> {
//				employee.setName(newEmployee.getName());
//				employee.setRole(newEmployee.getRole());
//				return repository.save(employee);
//			})
//			.orElseGet(() -> {
//				newEmployee.setId(id);
//				return repository.save(newEmployee);
//			});
	}
	
	
	

	
	// http://localhost:8080/api/path/employees/147/sometest/jest 
	@GetMapping("/employees/{id}/sometest/{name}")
	String getEmployee(@PathVariable Long id, @PathVariable String name) {
		return "ID="+ id+ " ===> name="+ name;
	}
	
	
	
	
}
