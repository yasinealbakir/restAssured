package api.endpoints;

import api.payload.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.text.MessageFormat;
import java.util.Locale;

import static io.restassured.RestAssured.given;
import static java.lang.System.out;

public class ExampleEndPoints {
    Faker fakeData = new Faker(new Locale("US"));
    String token = "3e52b3015a6a1ce06e7b1f2fbf38ae002f80491e342f7feb0220b17fbe1084aa";

    @Test(priority = 0)
    public void createUserTest(ITestContext context) throws JsonProcessingException {
        User user = new User();
        user.setName(fakeData.name().fullName());
        user.setGender("Male");
        user.setEmail(fakeData.internet().emailAddress());
        user.setStatus("active");

        RestAssured.useRelaxedHTTPSValidation();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonObject = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        int id = 0;
        id = given()
                .headers("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(jsonObject)
                .when()
                .post("https://gorest.co.in/public/v2/users")
                .jsonPath().getInt("id");

        out.println(MessageFormat.format("Created user id is --> {0}", id));
        context.setAttribute("user_id", id);

    }

    @Test(priority = 1, dependsOnMethods = "createUserTest")
    public void getUserTest(ITestContext context) {
        RestAssured.useRelaxedHTTPSValidation();
        int id = (int) context.getAttribute("user_id");

        given()
                .headers("Authorization", "Bearer " + token)
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .when()
                .get("https://gorest.co.in/public/v2/users/{id}")
                .then()
                .statusCode(200)
                .log().body();

        out.println(MessageFormat.format("Get user id is --> {0}", id));
    }

    @Test(priority = 2, dependsOnMethods = "createUserTest")
    public void updateUserTest(ITestContext context) throws JsonProcessingException {
        User user = new User();
        user.setName(fakeData.name().fullName());
        user.setGender("Male");
        user.setEmail(fakeData.internet().emailAddress());
        user.setStatus("active");

        RestAssured.useRelaxedHTTPSValidation();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonObject = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);

        int id = (int) context.getAttribute("user_id");

        given()
                .headers("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(jsonObject)
                .when()
                .put("https://gorest.co.in/public/v2/users/{id}")
                .then()
                .statusCode(200)
                .log().body();

        out.println(MessageFormat.format("Updated user id is --> {0}", id));
    }

    @Test(priority = 3, dependsOnMethods = "createUserTest")
    public void deleteUserTest(ITestContext context) throws JsonProcessingException {
        RestAssured.useRelaxedHTTPSValidation();
        int id = (int) context.getAttribute("user_id");

        given()
                .headers("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .delete("https://gorest.co.in/public/v2/users/{id}")
                .then()
                .statusCode(204)
                .log().body();

        out.println(MessageFormat.format("Deleted user id is --> {0}", id));
    }

}
