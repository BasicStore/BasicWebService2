package com.ws.ctrl;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ws.dm.Employee;
import com.ws.ex.EmployeeNotFoundException;
import com.ws.rep.EmployeeRepository;


@RestController
@RequestMapping("/api/path")
public class EmployeeController {

	private final EmployeeRepository repository;

	EmployeeController(EmployeeRepository repository) {
		this.repository = repository;
	}

	
	@GetMapping("/employees")
	List<Employee> getEmployees() {
		return repository.findAll();
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
	

	@GetMapping("/employees/{id}")
	Employee getEmployee(@PathVariable Long id) {

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
	
	
	

	@PutMapping("/employees/{id}")
	Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

		return repository.findById(id)
			.map(employee -> {
				employee.setName(newEmployee.getName());
				employee.setRole(newEmployee.getRole());
				return repository.save(employee);
			})
			.orElseGet(() -> {
				newEmployee.setId(id);
				return repository.save(newEmployee);
			});
	}

	@DeleteMapping("/employees/{id}")
	void deleteEmployee(@PathVariable Long id) {
		repository.deleteById(id);
	}

	
	
	// http://localhost:8080/api/path/employees/147/sometest/jest 
	@GetMapping("/employees/{id}/sometest/{name}")
	String getEmployee(@PathVariable Long id, @PathVariable String name) {
		return "ID="+ id+ " ===> name="+ name;
	}
	
	
	
	
}
