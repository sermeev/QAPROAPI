package User;

import dto.UserDTO;
import dto.ResponseDTO;
import io.restassured.response.ValidatableResponse;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.UserApi;

public class UserTest {
    UserDTO userDTOAllFields = new UserDTO().builder()
            .id(10011L)
            .username("yaroslava1961")
            .firstName("Ярослава")
            .lastName("Строганова")
            .email("yaroslava1961@mail.ru")
            .password("817036bfb")
            .phone("+7 (976) 997-15-34")
            .userStatus(1001L)
            .build();
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
    @DisplayName("Создание пользователя с указанием всех полей")
    public  void createUserTestAllFields(){
        UserApi userApi = new UserApi();
        ValidatableResponse response = userApi.createUser(userDTOAllFields).body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/responseCreateUser.json"));
        ResponseDTO responseDTO = response.extract().body().as(ResponseDTO.class);
        Assertions.assertAll("Response",
                ()->Assertions.assertEquals(200,responseDTO.getCode(),"Incorrect code"),
                ()->Assertions.assertEquals("unknown",responseDTO.getType(),"Incorrect type"),
                ()->Assertions.assertEquals("10011",responseDTO.getMessage(),"Incorrect message")
        );
    }
    @Test
    @DisplayName("Создание пользователя без указания username")
    public  void createUserTestNotFieldUsername(){
        UserApi userApi = new UserApi();
        ValidatableResponse response = userApi.createUser(userDTONotFieldUserName).body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/responseCreateUser.json"));
        ResponseDTO responseDTO = response.extract().body().as(ResponseDTO.class);
        Assertions.assertEquals(400,responseDTO.getCode(),"Incorrect code");
    }
    @Test
    @DisplayName("Получение пользователя по имени существующего пользователя")
    public  void getUserOnValidUserNameTest(){
        UserApi userApi = new UserApi();
                ValidatableResponse response = userApi.getUserOnUserName(userDTOAllFields);
                response.statusCode(200);
                UserDTO userDTO = response.extract().body().as(UserDTO.class);
                Assertions.assertEquals(userDTO,userDTOAllFields, "Incorrect user");
    }
    @Test
    @DisplayName("Получение пользователя по невалидной имени")
    public  void getUserOnUnValidUserNameTest(){
        UserApi userApi = new UserApi();
        UserDTO userDTONotValidUserName = new UserDTO().builder()
                                                        .username("+ * =")
                                                        .build();
        ValidatableResponse response = userApi.getUserOnUserName(userDTONotValidUserName);
        response.statusCode(400);
    }
}
