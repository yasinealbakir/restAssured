import Pojo.PostClass;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GenelTest {

    public JSONObject readJsonFile(String file) throws FileNotFoundException {
        File jsonFile = new File(file);
        FileReader fileReader = new FileReader(jsonFile);
        JSONTokener jsonTokener = new JSONTokener(fileReader);
        JSONObject jsonData = new JSONObject(jsonTokener);
        return jsonData;
    }

    @Test(priority = 1, testName = "TCKK Başvuru Id'ye Göre Kart Tipi Kontrolü")
    public void checkTckkCardTypeTest() {
        // Hashmap using
        HashMap data = new HashMap();
        data.put("basvuruId", "200000427");
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("http://localhost:7001/api/TckkNBSMachine/GetApplyKeyValue")

                .then()
                .body("cardType", equalTo("TCKK"))
                .body("isContactCoding", equalTo(true))
                .body("isContactLessCoding", equalTo(true))
                .statusCode(200)
                .log().all(); //all yerine body, cookies ve headers da spesific verilebilir.
    }

    @Test(priority = 1, testName = "Mavi Kart Başvuru Id'ye Göre Kart Tipi Kontrolü")
    public void checkBlueCardTypeTest() {
        // org.json library using
        JSONObject jsonData = new JSONObject();
        jsonData.put("basvuruId", "200000000226");

        given()
                .contentType(ContentType.JSON)
                .body(jsonData.toString()) //data stringe çevrilmezse hata alınıyor.
                .when()
                .post("http://localhost:7001/api/TckkNBSMachine/GetApplyKeyValue")

                .then()
                .body("cardType", equalTo("TCKK"))
                .body("isContactCoding", equalTo(false))
                .body("isContactLessCoding", equalTo(true))
                .statusCode(200)
                .log().all();
    }

    @Test(priority = 1, testName = "Meclis Kartı Başvuru Id'ye Göre Kart Tipi Kontrolü")
    public void checkMvdCardTypeTest() {
        // pojo class using
        PostClass pojoData = new PostClass();
        pojoData.setBasvuruId("200000000222");

        given()
                .contentType(ContentType.JSON)
                .body(pojoData)
                .when()
                .post("http://localhost:7001/api/TckkNBSMachine/GetApplyKeyValue")

                .then()
                .body("cardType", equalTo("TCKK"))
                .body("isContactCoding", equalTo(true))
                .body("isContactLessCoding", equalTo(false))
                .statusCode(200)
                .log().all();
    }

    @Test(priority = 1, testName = "Özel Güvenlik Kartı Başvuru Id'ye Göre Kart Tipi Kontrolü")
    public void checkSecurityCardTypeTest() throws FileNotFoundException {
        // external json file using
        given()
                .contentType(ContentType.JSON)
                .body(readJsonFile("src/test/resources/JsonFiles/body.json").toString())
                .when()
                .post("http://localhost:7001/api/TckkNBSMachine/GetApplyKeyValue")

                .then()
                .body("cardType", equalTo("TCKK"))
                .body("isContactCoding", equalTo(false))
                .body("isContactLessCoding", equalTo(false))
                .statusCode(200)
                .log().all();
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
        System.out.println("value of cookie is ----> " + cookie);

        //Get all cookies
        System.out.println("value of all cookies ---> " + res.getCookies());

        // Get all cookies in keyset
        Map<String, String> cookies_value = res.getCookies();
        System.out.println("cookies in keyset --> " + cookies_value.keySet());

        for (String k : cookies_value.keySet()) {
            String cookie_value = res.getCookie(k);
            System.out.println(k + ": " + cookie_value);
        }

    }

    @Test
    public void getHeaderTest() {
        Response res = given()
                .when()
                .get("https://onlineislemler.turktrust.com.tr/login.xhtml");

        String header_value = res.getHeader("Content-Type");
        System.out.println("Header value is ---> " + header_value);

        //All header key and value
        Headers headers = res.getHeaders();
        for (Header h : headers) {
            System.out.println(h.getName() + ": " + h.getValue());
        }
    }
}
