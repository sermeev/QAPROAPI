package services;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;

import dto.UserDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class UserApi {

    private static final String BASE_PATH = "/user";

    private  RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;


    public UserApi(){
        requestSpecification = given()
                                 .baseUri(System.getProperty("base.uri"))
                                 .basePath(BASE_PATH)
                                 .contentType(ContentType.JSON)
                                 .log().all();
        responseSpecification = expect()
                                 .contentType(ContentType.JSON)
                                 .log().all();
    }
    public ValidatableResponse createUser(UserDTO userDTO){
        return given(requestSpecification)
                .body(userDTO)
              .when()
                .post()
              .then()
                .log().all();
    }

    public ValidatableResponse deleteUser(UserDTO userDTO){
        return given(requestSpecification)
                .basePath(BASE_PATH+"/"+userDTO.getUsername())
                .when()
                .delete()
                .then()
                .log().all();
    }

    public ValidatableResponse getUserOnUserName(UserDTO userDTO){
        return given(requestSpecification)
                .basePath(BASE_PATH+"/"+userDTO.getUsername())
                .when()
                .get()
                .then()
                .spec(responseSpecification);
    }
    public ValidatableResponse loginUser(UserDTO userDTO){
        return given(requestSpecification)
                .basePath(BASE_PATH+"/login")
                .param("username",userDTO.getUsername())
                .param("password",userDTO.getPassword())
                .when()
                .get()
                .then()
                .log().all();
    }
}
