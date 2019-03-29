import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


public class basics { // Example to automate your RestApi tests

	@Test
	public void Test1() {
		// TODO Auto-generated method stub
		
		// First provide base URI or Host
		RestAssured.baseURI = "https://maps.googleapis.com";
		
		// Next step you need to provide all resources and parameters
		given().
			param("location","-33.8670522,151.1957362").
			param("radius","1500").
			param("key", "AIzaSyCoF2hciszijfoFZJw8fqYSC8vBMUB1ktc").
			
			when().
			get("/maps/api/place/nearbysearch/json").
			
			then().assertThat().statusCode(200). // status is always 200 to validate a get request
			and().contentType(ContentType.JSON).
			and().body("results[0].name", equalTo("Sydney")).and().
			body("results[0].place_id", equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM")).and().
			header("Server", "scaffolding on HTTPServer2"); 
			

	}

}
