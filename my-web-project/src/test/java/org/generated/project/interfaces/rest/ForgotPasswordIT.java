package org.generated.project.interfaces.rest;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.core.MediaType;

import org.generated.project.application.UpdatePassword;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.testing.junit4.SeedITRunner;
import org.seedstack.seed.undertow.LaunchWithUndertow;

import io.restassured.response.Response;

@RunWith(SeedITRunner.class)
@LaunchWithUndertow
public class ForgotPasswordIT {
	
	@Configuration("runtime.web.baseUrl")
    private String baseUrl;
	
	 @Test
	    public void testGenerateKey() throws Exception {
	        Response response = given()
	                .auth().basic("demo", "demo")
	                .expect().statusCode(200)
	                .when().get(baseUrl + "/psa/key/{id}","5065427");

	        assertThat(response.body().asString()).isGreaterThan("10000");
	    }
	 @Test
	    public void testGenerateKey2() throws Exception {
	        Response response = given()
	                .auth().basic("demo", "demo")
	                .expect().statusCode(200)
	                .when().get(baseUrl + "/psa/key/{id}","7");

	        assertThat(response.body().asString()).isEqualTo("0");
	    }
	 
	 @Test
	 public void updatepswd() throws Exception{
		 
		 UpdatePassword data=new UpdatePassword();
		 data.setEmployeeId("5065427");
		 data.setPassword("123456");
		 
		 Response response = given()
				 .contentType(MediaType.APPLICATION_JSON)
				 .body(data)
	                .auth().basic("demo", "demo")
	                .expect().statusCode(200)
	                .when().post(baseUrl + "/psa/updatepswd");
	               

	        assertThat(response.body().asString()).isEqualTo("Updated successfully");
		 
	 }
	 
	 @Test
	 public void updatepswd2() throws Exception{
		 
		 UpdatePassword data=new UpdatePassword();
		 data.setEmployeeId("7");
		 data.setPassword("123456");
		 
		 Response response = given()
				 .contentType(MediaType.APPLICATION_JSON)
				 .body(data)
	                .auth().basic("demo", "demo")
	                .expect().statusCode(200)
	                .when().post(baseUrl + "/psa/updatepswd");

	        assertThat(response.body().asString()).isEqualTo("updation failed");
		 
	 }
	 

}
