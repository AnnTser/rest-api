package homework;

import io.restassured.specification.RequestSpecification;
import models.lombok.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpec.loginRequestSpec;
import static specs.LoginSpec.missingPasswordSpec;
import static specs.UserSpec.*;


public class ApiWithSpecsModelsTests extends TestBase {
    @Tag("apispec")
    @Test
    void successfulRegisterWithSpecTest() {


        RegistrationBodyModel authBody = new RegistrationBodyModel();
        authBody.setEmail("eve.holt@reqres.in");
        authBody.setPassword("cityslicka");

        RegistrationResponseModel response = step("Make registration request", () ->

                given(userRequestSpec)
                        .body(authBody)
                        .when()
                        .post("/register")
                        .then()
                        .statusCode(200)
                        .extract().as(RegistrationResponseModel.class));

        step("Verify response", () ->
                assertAll(
                        () -> assertEquals("4", response.getId()),
                        () -> assertEquals("QpwL5tke4Pnpja7X4", response.getToken())
                ));


    }

    @Test
    void unSuccessfulRegisterTest() {
        LoginBodyLombokModel authBody = new LoginBodyLombokModel();
        authBody.setEmail("eve.holt@reqres.in");


        MissingPasswordModel response = step("Make login request", () ->
                given(loginRequestSpec)
                        .body(authBody)
                        .when()
                        .post()
                        .then()
                        .spec(missingPasswordSpec)
                        .extract().as(MissingPasswordModel.class));
        step("Verify response", () ->
                assertEquals("Missing password", response.getError()));
    }

    @Test
    void createUserWithSpecTest() {
        UserBodyModel createUser = new UserBodyModel();
        createUser.setName("Alex");
        createUser.setJob("freeloader");

        UserResponseModel response = step("Create user", () ->
                given(userCreateSpec)
                        .body(createUser)
                        .when()
                        .post("/users")
                        .then()
                        .spec(userResponseSpec)
                        .statusCode(201)
                        .extract().as(UserResponseModel.class));
        step("Verify response", () ->
                assertAll(
                        () -> assertEquals("Alex", response.getName()),
                        () -> assertEquals("freeloader", response.getJob())
                ));
    }

    @Test
    void updateUserWithSpecsTest() {
        UserBodyModel userUpdate = new UserBodyModel();
        userUpdate.setName("morpheus");
        userUpdate.setJob("zion resident");

        UserResponseModel response = step("Make update request", () ->
                given(userRequestSpec)
                        .body(userUpdate)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(userResponseSpec)
                        .extract().as(UserResponseModel.class));

        step("Verify changes", () ->
                assertAll(
                        () -> assertEquals("morpheus", response.getName()),
                        () -> assertEquals("zion resident", response.getJob())
                ));
    }


    @Test
    void deleteUserWithSpecsTest() {
        step("Make delete request", () ->
                given(userRequestSpec)
                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(userDeleteResponseSpec));
    }


    @Test
    void singleSourceNotFoundTest() {
        step("Make request for not found", () ->
                given(userRequestSpec)
                        .when()
                        .get("/users/23")
                        .then()
                        .spec(userResponseSpec)
                        .statusCode(404));
    }

}
