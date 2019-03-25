package com.ws.ctrl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.ws.ex.EmployeeNotFoundException;


@ControllerAdvice  // this advice is automatically triggered if an EmployeeNotFoundException is thrown in the EmployeeController method!!!! 
public class EmployeeNotFoundAdvice {
	
	@ResponseBody                                       // the advice of this method will go into the response body content
	@ExceptionHandler(EmployeeNotFoundException.class)  // configures advice to only respond to an EmployeeNotFoundException
	@ResponseStatus(HttpStatus.NOT_FOUND)               // issues a 404 http error code
	String employeeNotFoundHandler(EmployeeNotFoundException ex) {
		return ex.getMessage();
	}
	
	
}
