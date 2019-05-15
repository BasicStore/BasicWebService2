package com.ws.ctrl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.ctrl.EmployeeController;
import com.ws.ctrl.EmployeeNotFoundAdvice;
import com.ws.dm.Employee;
import com.ws.ex.EmployeeNotFoundException;
import com.ws.rep.CatFactory;


@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTests {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeControllerTests.class);
	
	private MockMvc mvc;
	
	@Mock
	private CatFactory catFactory;
	
	private JacksonTester<List<Employee>> jsonEmployeeList;
	
	private JacksonTester<Employee> jsonEmployee;
	
	
	@InjectMocks
	private EmployeeController employeeController;
	
	
	
	
	@Before
    public void setup() {
		logger.info("setting up here ..............");
		
		JacksonTester.initFields(this, new ObjectMapper());

		// create controller and add exception advice
        mvc = MockMvcBuilders.standaloneSetup(employeeController)
                .setControllerAdvice(new EmployeeNotFoundAdvice())
                .build();
	}
	
	
	// https://spring.io/guides/tutorials/rest/
	// https://thepracticaldeveloper.com/2017/07/31/guide-spring-boot-controller-tests/#Server-Side_Tests
	
	// https://github.com/mechero/spring-boot-testing-strategies/blob/master/src/test/java/io/tpd/superheroes/controller/SuperHeroControllerMockMvcStandaloneTest.java
//  curl -v localhost:8081/api/path/employees
	@Test
	public void doTestGetEmployees() throws Exception { 
		logger.info("doing test ..............");
		
		List<Employee> dummyEmployees = catFactory.getAllDummyEmployees();
		
		given(catFactory.getAllDummyEmployees()) // put in the value here
	        .willReturn(Arrays.asList(new Employee[] {
					new Employee(11L, "name1", "role1"),
					new Employee(21L, "name2", "role2"),
					new Employee(31L, "name3", "role3"),
					new Employee(41L, "name4", "role4"),
					new Employee(51L, "name5", "role5")}));
		
		// when
        MockHttpServletResponse response = mvc.perform(
                get("/api/path/employees")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		
		
		
        assertThat(response.getContentAsString()).isEqualTo(
        		jsonEmployeeList.write(Arrays.asList(new Employee[] {
    					new Employee(11L, "name1", "role1"),
    					new Employee(21L, "name2", "role2"),
    					new Employee(31L, "name3", "role3"),
    					new Employee(41L, "name4", "role4"),
    					new Employee(51L, "name5", "role5")})).getJson()
);
                
        
	}
	
	
	@Test
	public void doTestGetEmployeesNotFound() throws Exception { 
		logger.info("doing test ..............");
		
		//List<Employee> dummyEmployees = catFactory.getAllDummyEmployees();
		
		given(CatFactory.getAllDummyEmployees())
	        .willThrow(new EmployeeNotFoundException(1L));
		
		// when
        MockHttpServletResponse response = mvc.perform(
                get("/api/path/employees")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo("Could not find employee " + 1L);
	}
	
	
	
	
	// curl -X PUT localhost:8081/api/path/employees/3 -H 'Content-type:application/json' -d '{"name": "XXXSamwise Gamgee", "role": "XXXgardener"}'
	// Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
	@Test
	public void doTestUpdateEmployee() throws Exception {
		
		long id = 3;
		
		MockHttpServletResponse response = mvc.perform(
                put("/api/path/employees/" + id).contentType(MediaType.APPLICATION_JSON).content(
                		jsonEmployee.write(new Employee(id, "name1", "role1")).getJson()
                )).andReturn().getResponse();

        // then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		
		assertThat(response.getContentAsString()).isEqualTo(
				jsonEmployee.write(new Employee(id, "name1", "role1")).getJson()
				);
		
		logger.info("put response: " + response.getContentAsString());
		
		
	}
	
	
	@Test
	public void doTestUpdateEmployeeNotFound() throws Exception {
		
		long id = 3;
		
		MockHttpServletResponse response = mvc.perform(
                put("/api/path/employees/" + id).contentType(MediaType.APPLICATION_JSON).content(
                		jsonEmployee.write(new Employee(id, "name1", "role1")).getJson()
                )).andReturn().getResponse();

        // then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		
		
		
		
		
		logger.info("ZZZZZZZZZZZZZZZZ put response: " + response.getContentAsString());
		
		
	}
	
	
	

}
