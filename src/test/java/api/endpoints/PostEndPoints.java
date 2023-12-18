package api.endpoints;

import api.payload.Post;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static api.endpoints.Routes.POSTS_GET_URL;
import static api.endpoints.Routes.POSTS_POST_URL;
import static io.restassured.RestAssured.given;

public class PostEndPoints {

    public static Response createPost(Post payload) {
        RestAssured.useRelaxedHTTPSValidation();
        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(POSTS_POST_URL);
        return response;
    }

    public static Response getPost(int user_id) {
        RestAssured.useRelaxedHTTPSValidation();
        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("id", user_id)
                .when()
                .get(POSTS_GET_URL);
        return response;
    }
}
