package org.generated.project.interfaces.rest;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.core.MediaType;
import static io.restassured.RestAssured.given;
import org.generated.project.application.LoginCredential;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.testing.junit4.SeedITRunner;
import org.seedstack.seed.undertow.LaunchWithUndertow;

import io.restassured.response.Response;

@RunWith(SeedITRunner.class)
@LaunchWithUndertow
public class LoginIT {

	
	@Configuration("runtime.web.baseUrl")
    private String baseUrl;
	
	
	@Test
    public void testLogin() throws Exception {
        
        LoginCredential login = new LoginCredential();
        login.setId("5065427");
        login.setPassword("123456");
        
        Response response = given().
                contentType(MediaType.APPLICATION_JSON)
                 .body(login)
                .auth().basic("demo", "demo")
                .expect().statusCode(200)
                .when().post(baseUrl + "/psa/login");

 

        assertThat(response.body().asString()).isEqualTo("success");
    }
    @Test
    public void testLogin1() throws Exception {
        
        LoginCredential login = new LoginCredential();
        login.setId("5065427");
        login.setPassword("123556");
        
        Response response = given().
                contentType(MediaType.APPLICATION_JSON)
                 .body(login)
                .auth().basic("demo", "demo")
                .expect().statusCode(200)
                .when().post(baseUrl + "/psa/login");

 

        assertThat(response.body().asString()).isEqualTo("failed");
    }
    @Test
    public void testLogin2() throws Exception {
        
        LoginCredential login = new LoginCredential();
        login.setId("5066291");
        login.setPassword("123556");
        
        Response response = given().
                contentType(MediaType.APPLICATION_JSON)
                 .body(login)
                .auth().basic("demo", "demo")
                .expect().statusCode(200)
                .when().post(baseUrl + "/psa/login");

 

        assertThat(response.body().asString()).isEqualTo("Invalid EmployeeId");
    }
	
	
	
}
