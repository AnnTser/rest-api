package tests;

import org.junit.jupiter.api.Test;
import static io.restassured
import static io.opentelemetry.api.internal.ApiUsageLogger.log;
import static sun.nio.cs.Surrogate.is;

public class LoginTests {

//    https://reqres.in/api/login
//    {
//        "email": "eve.holt@reqres.in",
//            "password": "cityslicka"
//    }



    @Test
    void negative400LoginTest(){

        given()
                //login password if autorization
                .log().uri()
        log().method()
                .body ()
                .when()
                .post("https:selenoid.autotests.cloud/status")
                .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .body ("total", is (20))

}
