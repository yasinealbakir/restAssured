package api.endpoints;

import api.payload.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static api.endpoints.Routes.*;
import static io.restassured.RestAssured.given;

public class UserEndPoints {

    public static Response createUser(User payload) {
        RestAssured.useRelaxedHTTPSValidation();
        Response response = given()
                .contentType(ContentType.JSON)
                .headers("Authorization", "Bearer " + TOKEN)
                .body(payload)
                .when()
                .post(USER_POST_URL);
        return response;
    }

    public static Response getUser(int id) {
        RestAssured.useRelaxedHTTPSValidation();
        Response response = given()
                .contentType(ContentType.JSON)
                .headers("Authorization", "Bearer " + TOKEN)
                .pathParam("id", id)
                .when()
                .get(USER_GET_URL);
        return response;
    }

    public static Response updateUser(int id, User payload) {
        RestAssured.useRelaxedHTTPSValidation();
        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .headers("Authorization", "Bearer " + TOKEN)
                .body(payload)
                .when()
                .put(USER_PUT_URL);
        return response;

    }

    public static Response deleteUser(int id) {
        RestAssured.useRelaxedHTTPSValidation();
        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .headers("Authorization", "Bearer " + TOKEN)
                .when()
                .delete(USER_DELETE_URL);
        return response;

    }

}
