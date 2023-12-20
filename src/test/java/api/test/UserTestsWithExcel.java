package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import api.utilities.ExcelUtility;
import io.restassured.response.Response;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserTestsWithExcel {

    @Test(priority = 0, dataProvider = "user-data", dataProviderClass = DataProviders.class)
    public void createUserTest(String name, String gender, String email, String status) throws IOException {
        User payload = new User();
        payload.setName(name);
        payload.setGender(gender);
        payload.setEmail(email);
        payload.setStatus(status);

        Response response = UserEndPoints.createUser(payload);
        response.then().statusCode(201).log().body();

    }

}
