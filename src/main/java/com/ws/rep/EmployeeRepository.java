package com.ws.rep;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ws.dm.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	
}
