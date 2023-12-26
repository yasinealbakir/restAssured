package api.test;

import api.payload.Apply;
import api.payload.Student;
import api.payload.User;
import api.utilities.DataProviders;
import api.utilities.ExcelUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static api.endpoints.Routes.TOKEN;
import static api.utilities.JsonUtility.readJsonFile;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static java.lang.System.out;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasXPath;

public class ExampleTest {

    Faker fakeData = new Faker(new Locale("TR"));


    @Test
    public void googleTest() {
        RestAssured.useRelaxedHTTPSValidation();
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBody("");
        RequestSpecification spec = builder.build();
        RestAssured.baseURI = "https://www.turktrust.com.tr";
        Response res = given()
                .spec(spec)
                .when()
                .get("");
        String body = res.getBody().asPrettyString();
        out.println(body);
    }

    @Test
    public void firstTestScenario() {
        Response response = RestAssured.get("https://reqres.in/api/users?page=2");
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void firstBddTestScenario() {
        RestAssured.useRelaxedHTTPSValidation();
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test(priority = 0)
    public void createUserTest(ITestContext context) {
        RestAssured.baseURI = "https://reqres.in/api";
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", "Yasin");
        data.put("job", "Tester");

        Response response = given()
                .body(data)
                .when()
                .post("/users");

        context.setAttribute("userId", response.jsonPath().getInt("id"));
    }

    @Test(priority = 1, dependsOnMethods = "createUserTest")
    public void updateUserTest(ITestContext context) {
        RestAssured.baseURI = "https://reqres.in/api";
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", "Salih");
        data.put("job", "Teacher");
        String userId = (String) context.getAttribute("userId");

        given()
                .body(data)
                .when()
                .put("/users/" + userId)
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void orgJsonLibraryUsingTest() {
        RestAssured.useRelaxedHTTPSValidation();
        baseURI = "https://gorest.co.in/public/v2";

        JSONObject jsonData = new JSONObject();
        jsonData.put("name", "Hasan");
        jsonData.put("email", "hasan@test.com");
        jsonData.put("gender", "Male");
        jsonData.put("status", "active");

        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", "Bearer " + TOKEN)
                .body(jsonData.toString())
                .when()
                .post("/users")
                .then().statusCode(201);
    }


    @Test
    public void pojoClassUsingTest() {
        baseURI = "https://gorest.co.in/public/v2";

        User userData = new User();
        userData.setName("Hasan");
        userData.setEmail("hasan@abc.com");
        userData.setGender("Male");
        userData.setStatus("active");

        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", "Bearer " + TOKEN)
                .body(userData)
                .when()
                .post("/users")
                .then().statusCode(201);
    }

    @Test
    public void externalJsonFileTest() {
        baseURI = "https://gorest.co.in/public/v2";
        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", "Bearer " + TOKEN)
                .body(readJsonFile("userData.json").toString())
                .when()
                .post("/users")
                .then().statusCode(201);
    }

    @Test(dataProvider = "user-data", dataProviderClass = DataProviders.class)
    public void externalExelFileTest(String[] userData) {
        RestAssured.useRelaxedHTTPSValidation();
        baseURI = "https://gorest.co.in/public/v2";

        User user = new User();
        user.setName(userData[0]);
        user.setGender(userData[1]);
        user.setEmail(userData[2]);
        user.setStatus(userData[3]);

        given()
                .contentType(ContentType.JSON)
                .headers("Authorization", "Bearer " + TOKEN)
                .body(user)
                .when()
                .post("/users")
                .then().statusCode(201);
    }

    @Test
    public void queryAndPathParamTest() {
        //https://reqres.in/api/users?page=2&id=5

        given()
                .pathParam("mypath", "users")
                .queryParam("page", 2)
                .queryParam("id", 5)
                .when()
                .get("https://reqres.in/api/{mypath}")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void getCookieTest() {

        Response res = given()
                .when()
                .get("https://onlineislemler.turktrust.com.tr/login.xhtml");

        //Spesific cookie get key
        String cookie = res.getCookie("JSESSIONID");
        out.println("value of cookie is ----> " + cookie);

        //Get all cookies
        out.println("value of all cookies ---> " + res.getCookies());

        // Get all cookies in keyset
        Map<String, String> cookies_value = res.getCookies();
        out.println("cookies in keyset --> " + cookies_value.keySet());

        for (String k : cookies_value.keySet()) {
            String cookie_value = res.getCookie(k);
            out.println(k + ": " + cookie_value);
        }
        Assert.assertEquals(res.getStatusCode(), 200);

    }

    @Test
    public void getHeaderTest() {
        Response res = given()
                .when()
                .get("https://onlineislemler.turktrust.com.tr/login.xhtml");

        String header_value = res.getHeader("Content-Type");
        out.println("Header value is ---> " + header_value);

        //All header key and value
        Headers headers = res.getHeaders();
        for (Header h : headers) {
            out.println(h.getName() + ": " + h.getValue());
        }
    }

    @Test
    public void jsonObjectConvertTest() {
        HashMap data = new HashMap();
        data.put("basvuruId", "200000427");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("http://localhost:7001/api/TckkNBSMachine/GetApplyKeyValue");

        JSONObject jsonObject = new JSONObject(response.asString());
        out.println("JsonObject --> " + jsonObject);

        for (int i = 0; i < jsonObject.getJSONArray("applyKeyValueList").length(); i++) {
            String valueList = jsonObject.getJSONArray("applyKeyValueList").getJSONObject(i).get("value").toString();
            String keyList = jsonObject.getJSONArray("applyKeyValueList").getJSONObject(i).get("key").toString();
            out.println(keyList + ": " + valueList);
        }


    }

    @Test
    public void parsingJsonResponseBodyTest() {
        ;
        baseURI = "https://gorest.co.in/public/v2";
        String TOKEN = "e095...";
        Response response = given()
                .contentType(ContentType.JSON)
                .headers("Authorization", "Bearer " + TOKEN)
                .pathParam("id", 5850682)
                .when()
                .get("/users/{id}");

        Assert.assertEquals(response.jsonPath().getInt("id"), 5850682);
    }

    @Test
    public void parsingJsonResponseBodyTest2() {
        ;
        RestAssured.useRelaxedHTTPSValidation();
        baseURI = "https://reqres.in/api/users?page=2";
        Response response = given()
                .when()
                .get("");
        out.println(response.getBody().prettyPrint());
        String xx = response.jsonPath().getString("data[0].first_name");
        out.println(xx);
    }

    @Test
    public void validateXmlTest() {

//        given()
//                .pathParam("path", "Traveler")
//                .queryParam("page", 2)
//                .when()
//                .get("{path}")
//                .then()
////                .body("TravelerinformationResponse.page", equalTo("2"))
//                .body(hasXPath("/TravelerinformationResponse/page", equalTo("2")));

        baseURI = "http://restapi.adequateshop.com/api/";
        Response response = given()
                .pathParam("path", "Traveler")
                .queryParam("page", 2)
                .when()
                .get("{path}");

        String pageNumber = response.xmlPath().
                get("TravelerinformationResponse.page").toString();
        Assert.assertEquals(pageNumber, "2");

        String travelName = response.xmlPath().
                get("TravelerinformationResponse.travelers.Travelerinformation[0].name");
        Assert.assertEquals(travelName, "ASCAS");
    }

    @Test
    public void singleFileUploadTest() {
        File file = new File("C:\\Automation\\test.txt");
        given()
                .multiPart("file", file)
                .contentType(ContentType.MULTIPART)
                .when()
                .get("http://localhost:4200/uploadFile")
                .then()
                .statusCode(200)
                .log().all();

    }

    @Test
    public void multipleFileUploadTest() {

        File txtFile = new File("C:\\Automation\\test.txt");
        File pdfFile = new File("C:\\Automation\\test.pdf");

        File fileArray[] = {txtFile, pdfFile};
        given()
                .multiPart("files", fileArray)
                .contentType(ContentType.MULTIPART)
                .when()
                .get("http://localhost:4200/multipleUploadFiles")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void serializableTest() throws JsonProcessingException {
        // create java object using pojo class
        Student student = new Student();
        student.setName("Yasin");
        student.setLocation("Ankara");
        student.setPhone("0312 123 00 00");
        String course[] = {"Selenium", "JMeter", "CodeceptJS"};
        student.setCourses(course);

        // convert java object to json object (serialization)
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonObject = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(student);
        out.println(jsonObject);
    }

    @Test
    public void deSerializableTest() throws JsonProcessingException {
        String jsonData = "{\n" +
                "  \"name\" : \"Yasin\",\n" +
                "  \"location\" : \"Ankara\",\n" +
                "  \"phone\" : \"0312 123 00 00\",\n" +
                "  \"courses\" : [ \"Selenium\", \"JMeter\", \"CodeceptJS\" ]\n" +
                "}";

        //convert json data to java object
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = objectMapper.readValue(jsonData, Student.class);
        out.println(MessageFormat.format("Name: {0}", student.getName()));
        out.println(MessageFormat.format("Location: {0}", student.getLocation()));
        out.println(MessageFormat.format("Phone: {0}", student.getPhone()));
        out.println(MessageFormat.format("Courses: {0}", Arrays.toString(student.getCourses())));
    }

    @Test
    public void basicAuthenticationTest() {
        //Basic Authentication ile yapılan kimlik doğrulaması işlemlerinde encryption veya hash alma işlemleri kullanılmaz ve Base64 encode değeri clear-text olarak gönderilir.
        RestAssured.useRelaxedHTTPSValidation(); //SSL certificate by-pass
        given()
                .auth().basic("postman", "password")
                .when()
                .get("https://postman-echo.com/basic-auth")
                .then()
                .statusCode(200)
                .body("authenticated", equalTo(true));
    }

    @Test
    public void digestAuthenticationTest() {
        // Basic Authentication yönteminde olduğu gibi sadece base64 ile encode edip clear-text göndermek yerine belirli kombinasyonları rastgele bir nonce değeri ile MD5 hashini alarak verinin gizliliğini sağlar.
        RestAssured.useRelaxedHTTPSValidation(); //SSL certificate by-pass
        given()
                .auth().digest("postman", "password")
                .when()
                .get("https://postman-echo.com/basic-auth")
                .then()
                .statusCode(200)
                .body("authenticated", equalTo(true));
    }

    @Test
    public void preemptiveAuthenticationTest() {
        RestAssured.useRelaxedHTTPSValidation(); //SSL certificate by-pass
        given()
                .auth().preemptive().basic("postman", "password")
                .when()
                .get("https://postman-echo.com/basic-auth")
                .then()
                .statusCode(200)
                .body("authenticated", equalTo(true));
    }

    @Test
    public void bearerTokenAuthenticationTest() {
        RestAssured.useRelaxedHTTPSValidation(); //SSL certificate by-pass
        String token = "ghp_";
        given()
                .header("Authorization", MessageFormat.format("Bearer  {0}", token))
                .when()
                .get("https://api.github.com/user/repos")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void oAuth1AuthenticationTest() {
        given()
                .auth().oauth("consumerKey", "consumerSecret", "accessToken", "tokenSecret")
                .when()
                .get("url")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void oAuth2AuthenticationTest() {
        RestAssured.useRelaxedHTTPSValidation(); //SSL certificate by-pass
        String token = "ghp_d";
        given()
                .auth().oauth2(token)
                .when()
                .get("https://api.github.com/user/repos")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void apiKeyAuthenticationTest() {
        given()
                .queryParam("appid", "apiKey")
                .when()
                .get("api.url")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void fakerDataTest() throws JsonProcessingException {
        Student student = new Student();
        student.setName(fakeData.name().fullName());
        student.setLocation(fakeData.address().cityName());
        student.setPhone(fakeData.phoneNumber().cellPhone());
        String course[] = {"Selenium", "JMeter", "CodeceptJS"};
        student.setCourses(course);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonObject = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(student);
        out.println(jsonObject);
    }


}
