package user;

import dto.UserDTO;
import dto.ResponseDTO;
import io.restassured.response.ValidatableResponse;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.*;
import services.UserApi;

public class TestUserPositive {
    private UserDTO userDTOAllFields = new UserDTO().builder()
            .id(10011L)
            .username("yaroslava1961")
            .firstName("Ярослава")
            .lastName("Строганова")
            .email("yaroslava1961@mail.ru")
            .password("817036bfb")
            .phone("+7 (976) 997-15-34")
            .userStatus(1001L)
            .build();

    UserApi userApi = new UserApi();

    @Test
    @DisplayName("Создание пользователя с указанием всех полей")
    public  void testCreateUserAllFields(){

        ValidatableResponse response = userApi.createUser(userDTOAllFields).body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/responseCreateUser.json"));
        ResponseDTO responseDTO = response.extract().body().as(ResponseDTO.class);
        Assertions.assertAll("Response",
                ()->Assertions.assertEquals(200,responseDTO.getCode(),"Incorrect code"),
                ()->Assertions.assertEquals("unknown",responseDTO.getType(),"Incorrect type"),
                ()->Assertions.assertEquals("10011",responseDTO.getMessage(),"Incorrect message")
        );
        userApi.getUserOnUserName(userDTOAllFields);
        response = userApi.getUserOnUserName(userDTOAllFields);
        response.statusCode(200);
        UserDTO userDTO = response.extract().body().as(UserDTO.class);
        Assertions.assertEquals(userDTO,userDTOAllFields, "Incorrect user");
    }
    @Test
    @DisplayName("Проверка входа в систему пользователем")
    public  void testLoginUser(){
        ValidatableResponse response = userApi.createUser(userDTOAllFields).body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/responseCreateUser.json"));
        ResponseDTO responseDTO = response.extract().body().as(ResponseDTO.class);
        Assertions.assertAll("Response",
                ()->Assertions.assertEquals(200,responseDTO.getCode(),"Incorrect code"),
                ()->Assertions.assertEquals("unknown",responseDTO.getType(),"Incorrect type"),
                ()->Assertions.assertEquals("10011",responseDTO.getMessage(),"Incorrect message")
        );
        response = userApi.loginUser(userDTOAllFields).body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/responseCreateUser.json"));
        ResponseDTO responseDTO2= response.extract().body().as(ResponseDTO.class);
        Assertions.assertAll("Response",
                ()->Assertions.assertEquals(200,responseDTO2.getCode(),"Incorrect code"),
                ()->Assertions.assertEquals("unknown",responseDTO2.getType(),"Incorrect type"),
                ()->Assertions.assertTrue(responseDTO2.getMessage().contains("logged in user session:"))
        );

    }
    @AfterEach
    public void deleteUser(){
        ValidatableResponse response = userApi.deleteUser(userDTOAllFields);
        response.assertThat().statusCode(200);
    }
}
