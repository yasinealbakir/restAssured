package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

import java.util.Locale;

import static java.lang.System.out;

public class UserTests {

    Faker fakeData;
    User userPayload;

    @BeforeClass
    public void setup() {
        fakeData = new Faker(new Locale("US"));
        userPayload = new User();
        userPayload.setName(fakeData.name().fullName());
        userPayload.setGender("Male");
        userPayload.setEmail(fakeData.internet().emailAddress());
        userPayload.setStatus("active");
    }

    @Test(priority = 0)
    public void createUserTest(ITestContext context) {
        Response response = UserEndPoints.
                createUser(userPayload);
        response.then()
                .log().body()
                .statusCode(201);
        context.setAttribute("user_id", response.jsonPath().getInt("id"));
    }

    @Test(priority = 1)
    public void getUserTest(ITestContext context) {
        int id = (int) context.getAttribute("user_id");
        Response response = UserEndPoints.
                getUser(id);
        response.then()
                .log().body()
                .statusCode(200);
    }

    @Test(priority = 2)
    public void updateUserTest(ITestContext context) {
        int id = (int) context.getAttribute("user_id");
        userPayload.setStatus("inactive"); // just updated the 'status' field

        Response updateResponse = UserEndPoints.
                updateUser(id, userPayload);
        updateResponse.then()
                .statusCode(200);

        //Checking data after update operation
        Response getResponse = UserEndPoints.getUser(id);
        getResponse.then().
                body("status", equalTo("inactive"))
                .log().body()
                .statusCode(200);

    }

    @Test(priority = 3)
    public void deleteUserTest(ITestContext context) {
        int id = (int) context.getAttribute("user_id");
        Response deleteResponse = UserEndPoints.deleteUser(id);
        deleteResponse.then()
                .log().body()
                .statusCode(204);

        //Checking data after delete operation
        Response getResponse = UserEndPoints.getUser(id);
        getResponse.then().body("message", equalTo("Resource not found"))
                .log().body()
                .statusCode(404);
    }

}
