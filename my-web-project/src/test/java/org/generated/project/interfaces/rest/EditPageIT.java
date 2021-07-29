package org.generated.project.interfaces.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import javax.ws.rs.core.MediaType;


import org.generated.project.domain.model.Employee;
import org.generated.project.domain.model.EmployeeId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.testing.junit4.SeedITRunner;
import org.seedstack.seed.undertow.LaunchWithUndertow;
import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

@RunWith(SeedITRunner.class)
@LaunchWithUndertow
public class EditPageIT {
	
	@Configuration("runtime.web.baseUrl")
	private String baseUrl;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetEmployee() {
        Response response =given()
                .auth().basic("demo", "demo")
                .expect().statusCode(200)
                .when().get(baseUrl + "/psa/getemployee/{id}","5065427");

        assertThat(response.body().asString()).isNotEqualTo(new Employee());
		
		
	}

	@Test
	public void testUpdateEmployee() {
		Employee emp = new Employee();
		 emp.setId(new EmployeeId("5065427"));
		 emp.setName("Sourav");
		 emp.setEmail("srv.donkar@gmail.com");
		 emp.setDoj(new Date());
		 emp.setProjectId("61009");
		 emp.setProjectname("XYZ");
		 emp.setJobrole("Senior Consultant");
		 emp.setGender("Male");
		 emp.setPassword("123456");
		 
		 
		 Response response = given()
				 .contentType(MediaType.APPLICATION_JSON)
				 .body(emp)
	                .auth().basic("demo", "demo")
	                .expect().statusCode(200)
	                .when().post(baseUrl + "/psa/updateemployee");
	               

	        assertThat(response.body().asString()).isEqualTo("Success");
		 
	}
}
