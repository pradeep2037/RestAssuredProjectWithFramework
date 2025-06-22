package api.endpoints;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import api.payload.User;
import api.utilities.ReadingPropertiesFile;
import io.restassured.response.Response;

//User EndPoints.java is created to perform crud requests to the user API
//payload means data we are sending(POST REQUEST) to the server or data we are getting (GET REQUEST) in the response.

public class UserEndPoints {

	public static Response createUser(User payload) throws IOException {

		Response res = given().header("Content-Type", "application/json").header("accept", "application/json")
				.body(payload)

				.when().post(ReadingPropertiesFile.fileReading().getProperty("post_url"));

		return res;
	}
	

	public static Response readUser(String username) throws IOException {
		Response res = given()
				.pathParam("username", username)
				.when().get(ReadingPropertiesFile.fileReading().getProperty("get_url"));
		return res;
	}

	
	public static Response updateUser(String username, User payload) throws IOException {
		Response res = given()
				.header("Content-Type", "application/json")
				.header("accept", "application/json")
				.pathParam("username", username)
				.body(payload)
				.when().put(ReadingPropertiesFile.fileReading().getProperty("update_url"));
		return res;
	}

	public static Response deleteUser(String username) throws IOException {
		Response res = given()
				.pathParam("username", username)
				.when().delete(ReadingPropertiesFile.fileReading().getProperty("delete_url"));
		return res;
	}

}
