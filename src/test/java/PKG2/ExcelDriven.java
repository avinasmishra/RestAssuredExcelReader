package PKG2;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import resource.ExcelReader;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ExcelDriven {
	
	@Test
	public void excelTest() throws IOException
	{
		
		ExcelReader er = new ExcelReader();
		ArrayList data = er.getExcelData("AddBook");
	
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//using Hashmap		
		HashMap<String,Object> map = new HashMap<>();
		map.put("name", data.get(1));
		map.put("isbn", data.get(2));
		map.put("aisle", data.get(3));
		map.put("author", data.get(4));
		
		
		String resp = given().relaxedHTTPSValidation().log().all().contentType(ContentType.JSON)
				.body(map)
				.when().post("/Library/Addbook.php")
				.then().log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(resp);
		String Id = js.getString("ID");
		System.out.println("Id: "+Id);	
		
		
		//Delete API
		given().contentType(ContentType.JSON)
				.body("{\r\n"
						+ "    \"ID\": \""+Id+"\"\r\n"
						+ "}")
				.when().delete("/Library/DeleteBook.php")
				.then().log().all().extract().response().asString();
		
		
		
		
	}

}
