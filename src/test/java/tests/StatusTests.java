package tests;

import org.junit.jupiter.api.Test;

import static java.nio.file.Paths.get;

public class StatusTests {

 // request to   https://selenoid.autotests.cloud/status
   // get response https://selenoid.autotests.cloud/status


    @Test
    void checkTotalMini(){

        get("https:selenoid.autotests.cloud/status")
        .then()
        .body ("total", is (20));
    }
    @Test
    void checkTotalWhithLogs(){

        given()
        //login password if autorization
                //.log().all()
                .log().uri()

                .when()
                 get("https:selenoid.autotests.cloud/status")
                .then()
                .log().body()
                .log().status()
                .body ("total", is (20));
    }

    @Test
    void checkTotalWithStatus(){

        given()
        //login password if autorization
                .log().uri()

                .when()
                 get("https:selenoid.autotests.cloud/status")
                .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .body ("total", is (20));
    }  @Test
    void checkTotalAndChrome(){

        given()
        //login password if autorization
                .log().uri()
               log().method()
                .when()
                .get("https:selenoid.autotests.cloud/status")
                .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .body ("total", is (20))
                .body ("browsers.chrome", hasKey ("100.0");
    }

}
