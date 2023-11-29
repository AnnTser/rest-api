package homework;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;


public class ApiTests extends TestBase {

    @Test
    void successfulRegisterTest() {
        given()
                .log().uri()
                .log().method()
                .body("{\"email\": \"eve.holt@reqres.in\",\n\"password\": \"pistol\"}")
                .contentType(JSON)
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void unSuccessfulRegisterTest() {
        given()
                .log().uri()
                .log().method()
                .body("{\"email\": \"sydney@fife\"}")
                .contentType(JSON)
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void createUserTest() {
        given()
                .log().uri()
                .log().method()
                .body("{\"name\": \"morpheus\",\"job\": \"leader\"}")
                .contentType(JSON)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201);
    }

    @Test
    void updateUserTest() {
        given()
                .log().uri()
                .log().method()
                .body("{\"name\": \"morpheus\",\"job\": \"zion resident\"}")
                .contentType(JSON)
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200);
    }

    @Test
    void deleteUserTest() {
        given()
                .log().uri()
                .log().method()
                .when()
                .delete("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }


}
