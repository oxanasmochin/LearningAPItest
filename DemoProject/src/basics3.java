import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class basics3 {
	
	Properties prop=new Properties();
	@BeforeTest
	public void getData() throws IOException
	{
		
		FileInputStream fis =new FileInputStream("C:\\Users\\oxana\\git\\repository\\LearningApi\\DemoProject\\src\\files\\env.properties");
		prop.load(fis);
		//prop.get("HOST");
	}

	@Test
	
	public void AddandDelete()
	{
		
		String b ="{"+

  "\"location\": {"+

    "\"lat\": -33.8669710,"+

    "\"lng\": 151.1958750"+

  "},"+

  "\"accuracy\": 50,"+

  "\"name\": \"Google Shoes!\","+

  "\"phone_number\": \"(02) 9374 4000\","+

  "\"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\","+

  "\"types\": [\"shoe_store\"],"+

  "\"website\": \"http://www.google.com.au/\","+

  "\"language\": \"en-AU\""+"}";
		
		
		//Task 1 -grab the response
		RestAssured.baseURI = prop.getProperty("HOST");
		
		Response res = given().
		queryParam("key", prop.getProperty("KEY")).body(b).
		when().
		post("/maps/api/place/add/json"). //we added a new place id
		then().assertThat().statusCode(200).and().
		contentType(ContentType.JSON).and().
		body("status", equalTo("OK")). //response is in raw format
		
		extract().response();
		
		String convertedStringresponse = res.asString();//convert raw response into string
		System.out.println(convertedStringresponse);
		
		//Task 2 -grab the place ID from the response
		
		JsonPath js = new JsonPath(convertedStringresponse); //convert response into json
		String placeid = js.get("place_id");
		System.out.println(placeid);
		
		//Task 3 -place this place_id into delete request
		
		given().queryParam("key", prop.getProperty("KEY")).body("{\"place_id\": \"" + placeid + "\" }").
		when().
		post("/maps/api/place/delete/json"). //we deleted that created new place
		then().assertThat().statusCode(200).and().
		contentType(ContentType.JSON).and().
		body("status", equalTo("OK"));
	}
}
