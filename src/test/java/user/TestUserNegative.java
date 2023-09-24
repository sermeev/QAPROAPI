package user;

import dto.ResponseDTO;
import dto.UserDTO;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.UserApi;

public class TestUserNegative {
    UserDTO userDTONotFieldUserName = new UserDTO().builder()
            .id(10012L)
            .firstName("Jennie")
            .lastName("Nichols")
            .email("jennie.nichols@example.com")
            .password("addison")
            .phone("+7 (976) 997-15-34")
            .userStatus(2L)
            .build();
    @Test
    @DisplayName("Создание пользователя без указания username")
    public  void testCreateUserNotFieldUsername(){
        UserApi userApi = new UserApi();
        ValidatableResponse response = userApi.createUser(userDTONotFieldUserName).body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/responseCreateUser.json"));
        ResponseDTO responseDTO = response.extract().body().as(ResponseDTO.class);
        Assertions.assertEquals(400,responseDTO.getCode(),"Incorrect code");

    }
    @Test
    @DisplayName("Получение пользователя по невалидной имени")
    public  void testGetUserOnUnValidUserName(){
        UserApi userApi = new UserApi();
        UserDTO userDTONotValidUserName = new UserDTO().builder()
                                                        .username("+ * =")
                                                        .build();
        ValidatableResponse response = userApi.getUserOnUserName(userDTONotValidUserName);
        response.statusCode(400);
    }
}
